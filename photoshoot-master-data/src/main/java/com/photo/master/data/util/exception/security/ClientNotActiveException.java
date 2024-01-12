

package com.photo.master.data.util.exception.security;

import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;

import java.io.Serial;

public class ClientNotActiveException extends ClientAuthenticationException {

    @Serial
    private static final long serialVersionUID = 8342314842729693295L;

    public ClientNotActiveException() {
        super("Untuk proses verifikasi, silahkan klik tombol di bawah ini"); // Don't reveal source of error
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
