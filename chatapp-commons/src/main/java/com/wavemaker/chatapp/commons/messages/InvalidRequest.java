package com.wavemaker.chatapp.commons.messages;

/**
 * Created by sainihala on 14/7/16.
 */
public class InvalidRequest implements Message {

    private String info;

    public InvalidRequest(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public MessageType getType() {
        return MessageType.INVALID_REQUEST;
    }

}
