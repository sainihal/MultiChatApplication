package com.wavemaker.chatapp.commons.exceptions;

/**
 * Created by sainihala on 11/7/16.
 */
public class AppIOException extends RuntimeException {
    public AppIOException() {
        super();
    }

    public AppIOException(String message) {
        super(message);
    }

    public AppIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
