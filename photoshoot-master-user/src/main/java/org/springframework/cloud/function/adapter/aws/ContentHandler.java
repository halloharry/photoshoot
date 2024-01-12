package org.springframework.cloud.function.adapter.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reactivestreams.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.catalog.SimpleFunctionRegistry;
import org.springframework.cloud.function.context.config.RoutingFunction;
import org.springframework.cloud.function.json.JacksonMapper;
import org.springframework.cloud.function.json.JsonMapper;
import org.springframework.cloud.function.utils.FunctionClassUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.*;
import reactor.core.publisher.Flux;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class ContentHandler implements RequestStreamHandler {

    private static final Log logger = LogFactory.getLog(ContentHandler.class);

    private JsonMapper jsonMapper;

    private SimpleFunctionRegistry.FunctionInvocationWrapper function;

    public ContentHandler() {
        this.start();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        final byte[] payload = StreamUtils.copyToByteArray(input);

        if (logger.isInfoEnabled()) {
            logger.info("Received: " + new String(payload, StandardCharsets.UTF_8));
        }

        Object structMessage = this.jsonMapper.fromJson(payload, Object.class);
        log.info("struktur objek : {}", structMessage);
        boolean isApiGateway = structMessage instanceof Map
                && (((Map) structMessage).containsKey("httpMethod") ||
                (((Map) structMessage).containsKey("routeKey") && ((Map) structMessage).containsKey("version")));
        log.info("masuk api gateway : {}", isApiGateway);
        Message requestMessage;
        if (isApiGateway) {
            Object currentPayload = payload;
            Object targetObject = null;

            if (structMessage instanceof Map) {
                Map pathParameters = (Map) ((Map) structMessage).getOrDefault("pathParameters", null);
                String body = (String) ((Map) structMessage).getOrDefault("body", null);
                Map queryStringParameters =
                        (Map) ((Map) structMessage).getOrDefault("queryStringParameters", null);

                if (pathParameters != null && !pathParameters.isEmpty()) {
                    targetObject = pathParameters.values().toArray()[0];
                } else if (body != null && !body.isEmpty()) {
                    targetObject = body;
                } else if (queryStringParameters != null && !queryStringParameters.isEmpty()) {
                    targetObject = queryStringParameters;
                }
            }

            if (targetObject != null) {
                log.info("Target object: {}", targetObject);

                // In the case of generic message, we would like to strip the type part
                String typeName = function.getInputType().getTypeName().split("<")[0];
                log.info("Input type: {}", function.getInputType());
                if (Objects.equals(typeName, Message.class.getCanonicalName())) {
                    currentPayload = targetObject;
                } else {
                    currentPayload = jsonMapper.fromJson(targetObject, function.getInputType());
                }
            }
            log.info("Current payload: {}", currentPayload);

            MessageBuilder builder =
                    MessageBuilder.withPayload(currentPayload).setHeader(AWSLambdaUtils.AWS_API_GATEWAY, true);
            if (structMessage instanceof Map && ((Map) structMessage).containsKey("headers")) {
                builder.copyHeaders((Map) ((Map) structMessage).get("headers"));
            }
            requestMessage = builder.build();
        } else {
            log.info("keluar api gateway : {}", isApiGateway);
            requestMessage = AWSLambdaUtils
                    .generateMessage(
                            payload,
                            new MessageHeaders(Collections.emptyMap()),
                            function.getInputType(),
                            this.jsonMapper,
                            context
                    );
        }

        Object response = this.function.apply(requestMessage);
        log.info("function response : {}", response);
        logger.info("Start Build Result");
        byte[] responseBytes = this.buildResult(requestMessage, response);
        StreamUtils.copy(responseBytes, output);
        // any exception should propagate
    }

    @SuppressWarnings("unchecked")
    private byte[] buildResult(Message<?> requestMessage, Object output) throws IOException {
        logger.info("Process Build Result");
        Message<byte[]> responseMessage;
        if (output instanceof Publisher<?>) {
            List<Object> result = new ArrayList<>();
            for (Object value : Flux.from((Publisher<?>) output).toIterable()) {
                if (logger.isInfoEnabled()) {
                    logger.info("Response value: " + value);
                }
                result.add(value);
            }
            if (result.size() > 1) {
                output = result;
            } else {
                output = result.get(0);
            }

            if (logger.isInfoEnabled()) {
                logger.info("OUTPUT: " + output + " - " + output.getClass().getName());
            }

            byte[] payload = this.jsonMapper.toJson(output);
            responseMessage = MessageBuilder.withPayload(payload).build();
            log.info("response message build json mapper : {} ", responseMessage);
        } else {
            responseMessage = (Message<byte[]>) output;
            log.info("response message build json mapper : {} ", responseMessage);

        }
        log.info("response message final : {} ", AWSLambdaUtils.generateOutput(
                requestMessage,
                responseMessage,
                this.jsonMapper,
                function.getOutputType()
        ));

        return AWSLambdaUtils.generateOutput(
                requestMessage,
                responseMessage,
                this.jsonMapper,
                function.getOutputType()
        );
    }


    private void start() {
        Class<?> startClass = FunctionClassUtils.getStartClass();
        String[] properties = new String[]{
                "--spring.cloud.function.web.export.enabled=false",
                "--spring.main.web-application-type=none",
                "--spring.main.lazy-initialization=true",
                "--spring.jmx.enabled=false",
                "--spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false",
                "--spring.jpa.hibernate.ddl-auto=none",
                "--spring.data.jpa.repositories.bootstrap-mode=lazy",
                "--spring.main.banner-mode=off"
        };
        ConfigurableApplicationContext context =
                SpringApplication.run(new Class[]{startClass, AWSCompanionAutoConfiguration.class}, properties);

        Environment environment = context.getEnvironment();
        String functionName = environment.getProperty("spring.cloud.function.definition");
        FunctionCatalog functionCatalog = context.getBean(FunctionCatalog.class);
        this.jsonMapper = context.getBean(JsonMapper.class);
        if (this.jsonMapper instanceof JacksonMapper) {
            ((JacksonMapper) this.jsonMapper).configureObjectMapper(objectMapper -> {
                if (!objectMapper.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)) {
                    SimpleModule module = new SimpleModule();
                    module.addDeserializer(Date.class, new JsonDeserializer<Date>() {
                        @Override
                        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                                throws IOException {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(jsonParser.getValueAsLong());
                            return calendar.getTime();
                        }
                    });
                    objectMapper.registerModule(module);
                    objectMapper.registerModule(new JodaModule());
                    objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                }
            });
        }

        if (logger.isInfoEnabled()) {
            logger.info("Locating function: '" + functionName + "'");
        }

        this.function = functionCatalog.lookup(functionName, "application/json");

        Set<String> names = functionCatalog.getNames(null);
        if (this.function == null && !CollectionUtils.isEmpty(names)) {

            if (logger.isInfoEnabled()) {
                if (names.size() == 1) {
                    logger.info(
                            "Will default to RoutingFunction, since it is the only function available in " +
                                    "FunctionCatalog."
                                    + "Expecting 'spring.cloud.function.definition' or 'spring.cloud.function" +
                                    ".routing-expression' as Message headers. "
                                    + "If invocation is over API Gateway, Message headers can be provided as HTTP " +
                                    "headers.");
                } else {
                    logger.info("More then one function is available in FunctionCatalog. " + names
                            + " Will default to RoutingFunction, "
                            + "Expecting 'spring.cloud.function.definition' or 'spring.cloud.function" +
                            ".routing-expression' as Message headers. "
                            + "If invocation is over API Gateway, Message headers can be provided as HTTP" +
                            " headers.");
                }
            }
            this.function = functionCatalog.lookup(RoutingFunction.FUNCTION_NAME, "application/json");
        }

        if (this.function != null && this.function.isOutputTypePublisher()) {
            this.function.setSkipOutputConversion(true);
        }
        Assert.notNull(this.function, "Failed to lookup function " + functionName);

        if (!StringUtils.hasText(functionName)) {
            functionName = this.function.getFunctionDefinition();
        }

        if (logger.isInfoEnabled()) {
            logger.info("Located function: '" + functionName + "'");
        }
    }

}
