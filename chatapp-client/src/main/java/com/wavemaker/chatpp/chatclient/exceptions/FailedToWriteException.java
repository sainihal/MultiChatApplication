package com.wavemaker.chatpp.chatclient.exceptions;

/**
 * Created by sainihala on 5/7/16.
 */
public class FailedToWriteException extends RuntimeException {
    public FailedToWriteException(String message, Exception e) {
        super(message, e);
    }
}
