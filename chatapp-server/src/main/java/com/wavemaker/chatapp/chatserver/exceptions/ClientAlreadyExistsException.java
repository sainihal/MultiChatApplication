package com.wavemaker.chatapp.chatserver.exceptions;

/**
 * Created by sainihala on 19/7/16.
 */
public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException(String message) {
        super(message);
    }

}
