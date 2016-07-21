package com.wavemaker.chatapp.commons.ioservices;


import com.wavemaker.chatapp.commons.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by sainihala on 19/7/16.
 */
public class ObjectIOService implements IOService {

    public Message read(Socket socket) throws IOException, ClassNotFoundException {

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        Message message = (Message) objectInputStream.readObject();

        return message;
    }

    public void write(Socket socket, Message message) throws IOException {

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        objectOutputStream.writeObject(message);


    }

}
