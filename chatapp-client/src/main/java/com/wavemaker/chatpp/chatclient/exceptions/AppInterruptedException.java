package com.wavemaker.chatpp.chatclient.exceptions;

/**
 * Created by sainihala on 13/7/16.
 */
public class AppInterruptedException extends RuntimeException {

    public AppInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }

}

