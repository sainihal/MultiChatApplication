package com.wavemaker.chatapp.commons.messages;

/**
 * Created by sainihala on 12/7/16.
 */
public class ChatMessage implements Message {
    private String data;
    private String destination;
    private final String sender;

    public ChatMessage(String sender, String destination, String data) {
        this.sender = sender;
        this.destination = destination;
        this.data = data;
    }


    public MessageType getType() {
        return MessageType.CHAT;
    }

    public ChatMessage(String sender) {
        this.sender = sender;
    }

    public String getData() {
        return data;
    }

    public String getDestination() {
        return destination;
    }

    public String getSender() {
        return sender;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String toString() {
        return "Sender " + getSender() + ": " + getData() + " Destination:" + getDestination();
    }
}
