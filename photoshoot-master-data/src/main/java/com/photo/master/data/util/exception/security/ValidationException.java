package com.photo.master.data.util.exception.security;

public class ValidationException extends Exception {
    private static final long serialVersionUID = 8024895122045859786L;
    private final int validationCode;

    public ValidationException(String message) {
        super(message);
        this.validationCode = 200;
    }

    public ValidationException(String message, int validationCode) {
        super(message);
        this.validationCode = validationCode;
    }

    public ValidationException(String message, Throwable cause, int validationCode) {
        super(message, cause);
        this.validationCode = validationCode;
    }

    public int getValidationCode() {
        return this.validationCode;
    }
}
