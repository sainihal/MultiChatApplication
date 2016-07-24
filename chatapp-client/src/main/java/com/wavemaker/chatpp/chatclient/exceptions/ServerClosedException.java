package com.wavemaker.chatpp.chatclient.exceptions;

/**
 * Created by sainihala on 23/7/16.
 */
public class ServerClosedException extends Exception {
    public ServerClosedException(String message){
        super(message);
    }
}
