package com.wavemaker.chatapp.commons.messages;

/**
 * Created by sainihala on 13/7/16.
 */
public class RegistrationSuccess implements Message {
    private String name;

    public RegistrationSuccess(String name) {
        this.name = name;
    }

    public MessageType getType() {
        return MessageType.REGISTRATION_SUCCESS;
    }

    public String toString() {
        return "Client " + name + " registered successfully";
    }
}
