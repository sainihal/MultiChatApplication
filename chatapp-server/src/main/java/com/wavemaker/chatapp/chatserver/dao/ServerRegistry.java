package com.wavemaker.chatapp.chatserver.dao;

import java.net.Socket;

/**
 * Created by sainihala on 17/7/16.
 */
public interface ServerRegistry {
    boolean register(String name, Socket socket);

    void deRegister(String name);

    boolean exists(String name);

    Socket getSocket(String name);
}
