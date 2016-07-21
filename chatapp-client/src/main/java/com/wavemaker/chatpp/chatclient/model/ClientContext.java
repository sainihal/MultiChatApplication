package com.wavemaker.chatpp.chatclient.model;

import java.net.Socket;

/**
 * Created by sainihala on 12/7/16.
 */
public class ClientContext {
    private String name;
    private Socket socket;
    private boolean close;

    public ClientContext() {
    }

    public ClientContext(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isClosed() {
        return close;
    }

    public void setClosed(boolean close) {
        this.close = close;
    }
}
