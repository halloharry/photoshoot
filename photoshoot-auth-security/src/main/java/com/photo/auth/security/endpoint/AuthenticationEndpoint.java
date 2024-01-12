package com.photo.auth.security.endpoint;

import com.photo.master.data.dto.request.user.*;
import com.photo.master.data.dto.response.user.AuthenticationResponseDto;
import com.photo.master.data.dto.response.user.ResponseRegisterUserDto;
import com.photo.master.data.enumeration.ApplicationConstant;
import com.photo.master.data.util.IResultDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping(ApplicationConstant.ContextPath.API_AUTH)
//        consumes = MediaType.APPLICATION_JSON_VALUE,
//        produces = MediaType.APPLICATION_JSON_VALUE)
public interface AuthenticationEndpoint {

    @PostMapping(ApplicationConstant.ContextPath.REGISTER_USER)
    IResultDto<ResponseRegisterUserDto> register(@RequestBody RequestRegisterUserDto request, HttpServletRequest httpServletRequest);

    @PostMapping(ApplicationConstant.ContextPath.LOGIN)
    IResultDto<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto request, HttpServletRequest httpServletRequest);

    @PostMapping(ApplicationConstant.ContextPath.REFRESH_TOKEN)
    IResultDto<?> refreshToken(HttpServletRequest request, HttpServletResponse response, HttpServletRequest httpServletRequest) throws IOException;

    @PostMapping(ApplicationConstant.ContextPath.VERIFY)
    IResultDto<AuthenticationResponseDto> verifyCode(@RequestBody VerificationRequestDto verificationRequestDto, HttpServletRequest httpServletRequest);
}
