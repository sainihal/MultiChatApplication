package com.wavemaker.chatpp.chatclient.io;

import com.wavemaker.chatapp.commons.messages.ChatMessage;
import com.wavemaker.chatapp.commons.messages.Message;
import com.wavemaker.chatapp.commons.properties.Constants;
import com.wavemaker.chatpp.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatpp.chatclient.model.ClientContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

;

/**
 * Created by sainihala on 14/7/16.
 */
public class MessageReader {
    private static Logger logger = Logger.getLogger(MessageReader.class.getName());
    private BufferedReader br;

    public MessageReader() {
        br = new InputReader().createConsoleReader();
    }

    public MessageReader(String fileName) {
        br = new InputReader().createFileReader(fileName);
    }

    public Message getMessage(String sender, ClientContext clientContext)
            throws IOException, ClientClosedException {

        String destination;
        String data;

        logger.log(Level.INFO, "enter destination/ enter " + Constants.EXIT_KEY + " to exit");
        while (!br.ready()) {
            if (clientContext.isClosed()) {
                throw new ClientClosedException("client closed");
            }
        }
        destination = br.readLine();
        if (destination.equals(Constants.EXIT_KEY)) {
           throw new ClientClosedException("exit key detected");
        }
        logger.log(Level.INFO, "enter data");
        while (!br.ready()) {
            if (clientContext.isClosed()) {
                throw new ClientClosedException("chatclient closed");
            }
        }
        data = br.readLine();
        return new ChatMessage(sender, destination, data);
    }

    public void close() {
        try {
            br.close();
            logger.log(Level.INFO, "closed input reader");
        } catch (IOException ioe) {
            logger.log(Level.INFO, "exception in  closing input reader");
        }
    }
}