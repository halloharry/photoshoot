package com.photo.master.data.util.exception.security;

public class DaoException extends Exception {
    private static final long serialVersionUID = -2031181605742564468L;

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
