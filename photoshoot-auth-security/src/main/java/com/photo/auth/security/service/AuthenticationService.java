package com.photo.auth.security.service;

import com.photo.auth.security.ChangesPasswordRequest;
import com.photo.master.data.dto.request.user.*;
import com.photo.master.data.dto.response.user.AuthenticationResponseDto;
import com.photo.master.data.dto.response.user.ResponseRegisterUserDto;
import com.photo.master.data.util.exception.security.DuplicateDataException;
import com.photo.master.data.util.exception.security.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;

public interface AuthenticationService {

    ResponseRegisterUserDto registerUser(RequestRegisterUserDto registerUserDto) throws ServiceException, DuplicateDataException;

    AuthenticationResponseDto authenticate(AuthenticationRequestDto request) throws ServiceException;

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException;

    AuthenticationResponseDto verifyCode(VerificationRequestDto verificationRequestDto);
    Boolean changesPassword(ChangesPasswordRequest changesPasswordRequest, Principal connectedUser);

}
