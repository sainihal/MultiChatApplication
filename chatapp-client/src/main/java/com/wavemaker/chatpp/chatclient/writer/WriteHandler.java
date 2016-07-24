package com.wavemaker.chatpp.chatclient.writer;


import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.messages.Message;
import com.wavemaker.chatpp.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatpp.chatclient.io.InputMessageHandler;
import com.wavemaker.chatpp.chatclient.model.ClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;



/**
 * Created by sainihala on 14/7/16.
 */
public class WriteHandler {

    private static final Logger logger = LoggerFactory.getLogger(WriteHandler.class.getName());

    public static void writeToServer(Message message, ClientContext clientContext, IOService ioService)
            throws IOException {
        logger.info( message.toString());
        ioService.write(clientContext.getSocket(), message);
        if (message.getType() == Message.MessageType.QUIT) {
            clientContext.setClosed(true);
        }
        logger.info("Client:  data written...");
    }

    public static Message readInput(ClientContext clientContext, InputMessageHandler inputMessageHandler)
            throws IOException, ClientClosedException {
        Message message = inputMessageHandler.getMessage(clientContext);
        return message;
    }
}
