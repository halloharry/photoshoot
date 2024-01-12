

package com.photo.master.data.util.exception;

import java.io.Serial;

public class EndPointException extends Exception {
    @Serial
    private static final long serialVersionUID = -7697643370872599673L;

    public EndPointException(String message) {
        super(message);
    }

    public EndPointException(String message, Throwable cause) {
        super(message, cause);
    }
}
