package com.photo.auth.security.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photo.auth.security.ChangesPasswordRequest;
import com.photo.auth.security.config.JwtService;
import com.photo.auth.security.service.AuthenticationService;
import com.photo.auth.security.service.TwoFactorAuthenticationService;
import com.photo.master.data.dao.*;
import com.photo.master.data.dto.request.user.*;
import com.photo.master.data.dto.response.user.AuthenticationResponseDto;
import com.photo.master.data.dto.response.user.ResponseRegisterUserDto;
import com.photo.master.data.enumeration.TokenType;
import com.photo.master.data.model.user.*;
import com.photo.master.data.util.exception.security.DuplicateDataException;
import com.photo.master.data.util.exception.security.ServiceException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.*;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final IAuthUserDao authUserDao;
    private final IAuthUserProfileDao userProfileDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IAuthTokenDao tokenDao;
    private final TwoFactorAuthenticationService tfaService;

    private static AuthUserProfile getUserProfileDetail(RequestRegisterUserDto request, AuthUser savedUser) {
        AuthUserProfile authUserProfile = new AuthUserProfile();
        authUserProfile.setUser(savedUser);
        authUserProfile.setPhoneNumber(request.getUserProfile().getPhoneNumber());
        authUserProfile.setFirstName(request.getUserProfile().getFirstName());
        authUserProfile.setAddress(request.getUserProfile().getAddress());
        authUserProfile.setLastName(request.getUserProfile().getLastName());
        authUserProfile.setGender(request.getUserProfile().getGender());
        authUserProfile.setBirtDate(LocalDate.now());
        return authUserProfile;
    }

    @Override
    public ResponseRegisterUserDto registerUser(RequestRegisterUserDto request) throws ServiceException, DuplicateDataException {
        AuthUser userMobilePhone = authUserDao.findByUserProfilePhoneNumber(request.getUserProfile().getPhoneNumber());
        if (userMobilePhone != null) {
            throw new ServiceException("user phone number already taken");

        }
        AuthUser userEmail = authUserDao.findByEmail(request.getEmail());
        if (userEmail != null) {
            throw new DuplicateDataException("user email already taken");

        }

        AuthUser authUser = new AuthUser();
        authUser.setUsername(request.getUsername());
        authUser.setEmail(request.getEmail());
        authUser.setPassword(passwordEncoder.encode(request.getPassword()));
        authUser.setRole(request.getRole());
        authUser.setLoginStatus(false);
        authUser.setMfaEnabled(request.isMfaEnabled());
        AuthUser savedUser = authUserDao.save(authUser);

        userProfileDao.save(getUserProfileDetail(request, savedUser));

        // if MFA enabled --> Generate Secret
        if (request.isMfaEnabled()) {
            savedUser.setSecret(tfaService.generateNewSecret());
        }
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        savedUserToken(savedUser, jwtToken);

        return ResponseRegisterUserDto
                .builder()
//                .secretImageUri(Objects.nonNull(savedUser.getSecret()) ? tfaService.generateQrCodeImageUri(savedUser.getSecret()) : "")
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
//                .mfaEnabled(savedUser.isMfaEnabled())
                .build();
    }

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) throws ServiceException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = authUserDao.findByEmail(request.getEmail());
        if (user == null) {
            throw new ServiceException("user email not found");
        }
        if (user.isMfaEnabled()) {
            return AuthenticationResponseDto.builder()
                    .accessToken("")
                    .refreshToken("")
                    .mfaEnabled(true)
                    .build();
        }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokedAllUserTokens(user);
        savedUserToken(user, jwtToken);
        return AuthenticationResponseDto
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .mfaEnabled(false)
                .build();
    }

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServiceException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServiceException("authentication header is null or is not using Bearer");
        }
        refreshToken = authHeader.substring(7); // 7 is after "BEARER "
        // user email from JWT
        userEmail = jwtService.extractUsername(refreshToken);

        // check if user is not authenticate
        if (userEmail != null) {
            var user = this.authUserDao.findByEmail(userEmail); // get user from database
            if (user == null) {
                throw new ServiceException("user email not found");
            }
            // validate refresh token
            if (jwtService.tokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokedAllUserTokens(user);
                savedUserToken(user, accessToken);
                var authResponse = AuthenticationResponseDto
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .mfaEnabled(false)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public AuthenticationResponseDto verifyCode(
            VerificationRequestDto verificationRequestDto
    ) {
        AuthUser user = authUserDao
                .findByEmail(verificationRequestDto.getEmail());
        if (user == null) {
            throw new EntityNotFoundException(
                    String.format("No user found with %S", verificationRequestDto.getEmail()));

        }
        if (tfaService.isOtpNotValid(user.getSecret(), verificationRequestDto.getCode())) {

            throw new BadCredentialsException("Code is not correct");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }

    @Override
    public Boolean changesPassword(ChangesPasswordRequest request, Principal connectedUser) {
        var user = (AuthUser) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        // check current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return false;
        }
        // check password is the same
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        authUserDao.save(user);
        return true;
    }

    private void savedUserToken(AuthUser user, String jwtToken) {
        var token = AuthToken
                .builder()
                .authUser(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenDao.save(token);
    }

    private void revokedAllUserTokens(AuthUser user) {
        var validToken = tokenDao.findAllByValidTokenByUser(user.getId());
        if (validToken.isEmpty()) {
            return;
        }
        validToken.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenDao.saveAll(validToken);
    }
}
