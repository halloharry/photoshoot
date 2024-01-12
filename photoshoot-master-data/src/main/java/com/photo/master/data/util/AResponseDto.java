package com.photo.master.data.util;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import jakarta.xml.bind.annotation.XmlRootElement;

@Getter
@XmlRootElement
public abstract class AResponseDto {
    @JsonProperty("responseCode")
    @JsonDeserialize(
            converter = ResponseCodeConverter.class
    )
    private Integer responseCode;
    private String responseMsg;

    public void setResponseCode(Integer value) {
        this.responseCode = value;
    }

    @JsonIgnore
    public void setResponseCode(HttpStatus value) {
        this.responseCode = value.value();
    }

    public boolean equals(HttpStatus o) {
        return this.responseCode.equals(o.value());
    }

    public void setResponseMsg(final String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public AResponseDto() {
    }

    public AResponseDto(final Integer responseCode, final String responseMsg) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
    }

    private static class ResponseCodeConverter extends StdConverter<String, Integer> {
        private ResponseCodeConverter() {
        }

        public Integer convert(String value) {
            return Integer.valueOf(value.replaceAll("\\D+", ""));
        }
    }
}
