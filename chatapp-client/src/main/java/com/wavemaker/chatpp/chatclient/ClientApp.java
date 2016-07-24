package com.wavemaker.chatpp.chatclient;


import com.wavemaker.chatapp.commons.exceptions.AppIOException;
import com.wavemaker.chatapp.commons.properties.PropertyLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



/**
 * Created by sainihala on 23/6/16.
 */
public class ClientApp {
    private static final String DEFAULT_HOST = "localhost";
    private static final Logger logger = LoggerFactory.getLogger(ClientApp.class);


    public static void main(String args[]) {
        String hostName = DEFAULT_HOST;
        int port;
        String clientName;
        BufferedReader br = null;

        if (args.length > 0) {
            clientName = args[0];
        } else {
            logger.info("enter the name of chatclient");
            try {
                br = new BufferedReader(new InputStreamReader(System.in));
                clientName = br.readLine();
            } catch (IOException e) {
                throw new AppIOException("In reading chatclient name", e);
            }
        }
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        } else {
            port = PropertyLoader.getPort();
        }
        if (args.length > 2) {
            hostName = args[3];
        }
        ChatClient chatClient = new ChatClient(hostName, port, clientName);
        chatClient.start();
        try {
            br.close();
        } catch (IOException ioe) {
            logger.error("In closing buffered reader ", ioe);
        }
    }
}

