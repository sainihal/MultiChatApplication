package com.wavemaker.chatpp.chatclient.exceptions;

/**
 * Created by sainihala on 13/7/16.
 */
public class RegistrationFailedException extends RuntimeException {
    public RegistrationFailedException(String message) {
        super(message);
    }
    public RegistrationFailedException(Throwable cause){
        super(cause);
    }

}

