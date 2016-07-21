package com.wavemaker.chatapp.chatserver.dao;

import com.wavemaker.chatapp.chatserver.exceptions.ClientAlreadyExistsException;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sainihala on 24/6/16.
 */
public class ServerRegistryImpl implements ServerRegistry {
    public Map<String, Socket> socketByName;

    public ServerRegistryImpl() {
        socketByName = new ConcurrentHashMap<String, Socket>();
    }

    public boolean register(String name, Socket socket) {
        if (socketByName.containsKey(name)) {
            throw new ClientAlreadyExistsException("Client already exists");
        } else {
            socketByName.put(name, socket);
        }
        return true;
    }

    public void deRegister(String name) {
        socketByName.remove(name);
    }

    public Socket getSocket(String name) {
        return socketByName.get(name);
    }

    public boolean exists(String name) {
        return (socketByName.containsKey(name));
    }


}
