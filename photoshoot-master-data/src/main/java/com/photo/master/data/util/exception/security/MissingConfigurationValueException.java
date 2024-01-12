package com.photo.master.data.util.exception.security;

public class MissingConfigurationValueException extends IllegalStateException {
    private static final long serialVersionUID = -2031181605742564468L;

    public MissingConfigurationValueException(String message) {
        super(message);
    }

    public MissingConfigurationValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
