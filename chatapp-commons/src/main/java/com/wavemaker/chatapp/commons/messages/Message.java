package com.wavemaker.chatapp.commons.messages;

import java.io.Serializable;

/**
 * Created by sainihala on 12/7/16.
 */
public interface Message extends Serializable {

    enum MessageType {
        CHAT, REGISTER, QUIT, REGISTRATION_SUCCESS, REGISTRATION_FAILED, SERVER_EXITING,
        NO_CLIENT_EXISTS
    }

    MessageType getType();

}
