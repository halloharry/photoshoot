package com.photo.master.user.functions;

import com.photo.master.data.util.IResultDto;
import com.photo.master.data.util.core.APIResponseBuilder;
import com.photo.master.user.service.RegisterSellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

@Slf4j
@SuppressWarnings("unused")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JavaTestFunction implements Function<String, IResultDto<String>> {

    private final RegisterSellerService registerSellerService;

    @Override
    public IResultDto<String> apply(String s) {
        registerSellerService.registerSeller1();
        return APIResponseBuilder.ok("helo");
    }
}
