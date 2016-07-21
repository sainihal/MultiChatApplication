package com.wavemaker.chatapp.chatserver;


import com.wavemaker.chatapp.chatserver.dao.ServerRegistryImpl;
import com.wavemaker.chatapp.chatserver.model.ServerContext;
import com.wavemaker.chatapp.commons.exceptions.AppIOException;
import com.wavemaker.chatapp.commons.properties.Constants;
import com.wavemaker.chatapp.commons.properties.PropertyLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sainihala on 23/6/16.
 */
public class ServerApp {
    private static final Logger logger = Logger.getLogger(ServerApp.class.getName());

    public ServerApp() {
    }

    public static void main(String args[]) {
        ServerContext serverContext = new ServerContext(new ServerRegistryImpl());
        int port = (args.length == 0) ? PropertyLoader.getPort() : Integer.parseInt(args[0]);
        String status = null;
        ChatServer chatServer = new ChatServer(port, serverContext);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Thread thread = new Thread(chatServer);
        thread.start();
        logger.log(Level.INFO, "enter " + Constants.EXIT_KEY + " to close the server");

        try {
            outerloop:
            while (!Constants.EXIT_KEY.equals(status) && !serverContext.isClosed()) {
                while (!br.ready()) {
                    if (serverContext.isClosed()) {
                        break outerloop;
                    }
                }
                status = br.readLine();
            }
        } catch (IOException e) {
            throw new AppIOException("In Chat server invoker ", e);
        }
        if (status.equals(Constants.EXIT_KEY)) {
            chatServer.closeClients();
        }
    }
}
