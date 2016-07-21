package com.wavemaker.chatapp.commons.exceptions;

/**
 * Created by sainihala on 11/7/16.
 */

public class AppClassNotFoundException extends RuntimeException {
    public AppClassNotFoundException() {
    }

    public AppClassNotFoundException(String message) {
        super(message);
    }

    public AppClassNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
