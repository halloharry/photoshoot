

package com.photo.master.data.util.exception.security;


import java.io.Serial;

public class DuplicateDataException extends Exception {

    @Serial
    private static final long serialVersionUID = 8024895122045859786L;

    public DuplicateDataException(String message) {
        super(message);
    }

    public DuplicateDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
