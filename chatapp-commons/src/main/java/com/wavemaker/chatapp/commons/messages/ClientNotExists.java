package com.wavemaker.chatapp.commons.messages;

/**
 * Created by sainihala on 14/7/16.
 */
public class ClientNotExists implements Message {

    public MessageType getType() {
        return MessageType.NO_CLIENT_EXISTS;
    }

    public String toString() {
        return "No Client with specified name exists";
    }
}
