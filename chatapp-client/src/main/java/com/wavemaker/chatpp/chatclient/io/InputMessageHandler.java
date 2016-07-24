package com.wavemaker.chatpp.chatclient.io;

import com.wavemaker.chatapp.commons.messages.ChatMessage;
import com.wavemaker.chatapp.commons.messages.Message;
import com.wavemaker.chatapp.commons.messages.QuitMessage;
import com.wavemaker.chatapp.commons.properties.Constants;
import com.wavemaker.chatpp.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatpp.chatclient.model.ClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



/**
 * Created by sainihala on 14/7/16.
 */
public class InputMessageHandler {
    private static Logger logger = LoggerFactory.getLogger(InputMessageHandler.class.getName());
    private BufferedReader br;

    public InputMessageHandler() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public Message getMessage(ClientContext clientContext)
            throws IOException, ClientClosedException {
        String destination;
        String data;

        logger.info("enter destination/ enter {} to exit", Constants.EXIT_KEY );
        waitTillReady(clientContext);
        destination = br.readLine();
        if (destination.equals(Constants.EXIT_KEY)) {
            return new QuitMessage(clientContext.getName());
        }
        logger.info("enter data");
        waitTillReady(clientContext);
        data = br.readLine();
        return new ChatMessage(clientContext.getName(), destination, data);
    }

    public void close() {
        try {
            br.close();
            logger.info("closed input reader");
        } catch (IOException ioe) {
            logger.error("In  closing input reader",ioe);
        }
    }

    private void waitTillReady(ClientContext clientContext) throws IOException,ClientClosedException{
        while (!br.ready()) {
            if (clientContext.isClosed()) {
                throw new ClientClosedException("client closed");
            }
        }
    }
}


