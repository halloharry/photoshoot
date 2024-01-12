package com.photo.master.data.util.exception.security;

public class ServiceException extends Exception {
    private static final long serialVersionUID = 8024895122045859786L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
