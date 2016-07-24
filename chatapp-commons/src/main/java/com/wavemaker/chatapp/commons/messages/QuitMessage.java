package com.wavemaker.chatapp.commons.messages;

import java.io.Serializable;

/**
 * Created by sainihala on 13/7/16.
 */
public class QuitMessage implements Serializable, Message {
    private String sender;

    public QuitMessage() {
    }

    public QuitMessage(String sender) {
        this.sender = sender;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public MessageType getType() {
        return MessageType.QUIT;
    }

    public String toString() {
        return "terminating.... " + getSender();
    }

}
