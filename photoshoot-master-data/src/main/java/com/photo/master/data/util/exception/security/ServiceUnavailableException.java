package com.photo.master.data.util.exception.security;

public class ServiceUnavailableException extends Exception {
    private static final long serialVersionUID = 8024895122045859786L;

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
