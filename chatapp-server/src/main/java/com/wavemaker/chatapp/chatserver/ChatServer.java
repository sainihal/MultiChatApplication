package com.wavemaker.chatapp.chatserver;


import com.wavemaker.chatapp.chatserver.model.ClientContext;
import com.wavemaker.chatapp.chatserver.model.ServerContext;
import com.wavemaker.chatapp.chatserver.worker.ClientWorker;
import com.wavemaker.chatapp.chatserver.exceptions.ClientAlreadyExistsException;
import com.wavemaker.chatapp.commons.exceptions.AppIOException;
import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.objectfactory.ObjectFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

;

/**
 * Created by sainihala on 24/6/16.
 */
public class ChatServer implements Runnable {

    private static final Logger lOGGER = Logger.getLogger(ServerApp.class.getName());
    private ObjectFactory objectFactory;
    private IOService service;
    private List<Thread> threadsList;
    private ServerSocket serverSocket;
    private int port;
    private ServerContext serverContext;

    public ChatServer(int port, ServerContext serverContext) {
        this.port = port;
        this.serverContext = serverContext;
        this.objectFactory = new ObjectFactory();
        this.service = objectFactory.getObjectIOService();
        this.threadsList = new ArrayList<Thread>();
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            serverContext.setClosed(true);
            throw new AppIOException("In  ServerSocket creation ", e);
        }
        try {
            while (!serverContext.isClosed()) {
                Socket socket = serverSocket.accept();
                try {
                    ClientContext clientContext = ClientRegisterer.registerClient(socket, serverContext, service);
                    Thread serverThread = new Thread(new ClientWorker(clientContext, service));
                    threadsList.add(serverThread);
                    serverThread.start();
                } catch (ClientAlreadyExistsException clientAlreadyExists) {
                    lOGGER.log(Level.INFO, "", clientAlreadyExists);
                }
            }
        } catch (IOException e) {
            closeClients();
            lOGGER.log(Level.SEVERE, "In server ", e);
        }
    }

    public void closeClients() {
        serverContext.setClosed(true);
        for (Thread thread : threadsList) {
            thread.interrupt();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            lOGGER.log(Level.SEVERE, "In  server socket closing", e);
        }
    }
}



