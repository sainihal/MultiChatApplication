package com.wavemaker.chatpp.chatclient.exceptions;

/**
 * Created by sainihala on 5/7/16.
 */
public class FailedToReadException extends RuntimeException {
    public FailedToReadException(String message, Exception e) {
        super(message, e);
    }
}
