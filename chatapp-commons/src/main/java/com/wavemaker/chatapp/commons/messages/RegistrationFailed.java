package com.wavemaker.chatapp.commons.messages;

import java.io.Serializable;

/**
 * Created by sainihala on 13/7/16.
 */
public class RegistrationFailed implements Serializable, Message {
    private String data;

    public RegistrationFailed(String data) {
        this.data = data;
    }

    public MessageType getType() {
        return MessageType.REGISTRATION_FAILED;
    }

    public String toString() {
        return data;
    }
}
