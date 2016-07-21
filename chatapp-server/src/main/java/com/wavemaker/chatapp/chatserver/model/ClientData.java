package com.wavemaker.chatapp.chatserver.model;

import java.net.Socket;

/**
 * Created by sainihala on 18/7/16.
 */
public class ClientData {
    private Socket socket;
    private String name;

    public ClientData(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public Socket getSocket() {

        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
