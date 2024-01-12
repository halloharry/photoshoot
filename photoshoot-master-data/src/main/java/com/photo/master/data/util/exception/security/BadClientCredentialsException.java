
package com.photo.master.data.util.exception.security;

import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;

import java.io.Serial;

public class BadClientCredentialsException extends ClientAuthenticationException {

    @Serial
    private static final long serialVersionUID = 6954474205538862386L;

    public BadClientCredentialsException() {
        super("Nama pengguna atau password yang diberikan tidak sesuai.");
    }

    public BadClientCredentialsException(String msg) {
        super(msg);
    }

    @Override
    public int getHttpErrorCode() {
        return 401;
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "invalid_client";
    }
}
