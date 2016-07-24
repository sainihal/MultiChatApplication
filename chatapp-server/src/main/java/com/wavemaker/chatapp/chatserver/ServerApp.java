package com.wavemaker.chatapp.chatserver;


import com.wavemaker.chatapp.chatserver.dao.ServerRegistryImpl;
import com.wavemaker.chatapp.chatserver.model.ServerContext;
import com.wavemaker.chatapp.commons.exceptions.AppIOException;
import com.wavemaker.chatapp.commons.properties.Constants;
import com.wavemaker.chatapp.commons.properties.PropertyLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by sainihala on 23/6/16.
 */
public class ServerApp {
    private static final Logger logger = LoggerFactory.getLogger(ServerApp.class.getName());

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
        logger.info("enter {} to close the server", Constants.EXIT_KEY);

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
        if (status != null && status.equals(Constants.EXIT_KEY)) {
            chatServer.closeClients();
        }
        try {
            br.close();
        } catch (IOException ioe) {
            logger.error("In closing buffered reader", ioe);
        }
    }
}
