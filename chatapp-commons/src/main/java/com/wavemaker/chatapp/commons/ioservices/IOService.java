package com.wavemaker.chatapp.commons.ioservices;


import com.wavemaker.chatapp.commons.messages.Message;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by sainihala on 19/7/16.
 */
public interface IOService {
    Message read(Socket socket) throws IOException, ClassNotFoundException;

    void write(Socket socket, Message message) throws IOException;
}
