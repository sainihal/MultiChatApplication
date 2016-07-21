package com.wavemaker.chatpp.chatclient.writer;


import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.messages.ChatMessage;
import com.wavemaker.chatapp.commons.messages.Message;
import com.wavemaker.chatpp.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatpp.chatclient.io.MessageReader;
import com.wavemaker.chatpp.chatclient.model.ClientContext;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

;

/**
 * Created by sainihala on 14/7/16.
 */
public class WriteHandler {

    private static final Logger logger = Logger.getLogger(WriteHandler.class.getName());

    public static void writeToServer(Message message, Socket socket, IOService ioService)
            throws IOException {
        ioService.write(socket, message);
        logger.log(Level.INFO, "Client:  data written...");
    }

    public static Message readInput(ClientContext clientContext, MessageReader messageReader)
            throws IOException, ClientClosedException {
        Message message;

        message = messageReader.getMessage(clientContext.getName(), clientContext);

        if (message.getType() == Message.MessageType.QUIT) {
            clientContext.setClosed(true);
            logger.log(Level.INFO, "closing the chatclient");
            return message;
        } else {
            logger.log(Level.INFO, "destination is " + ((ChatMessage) message).getDestination());
            logger.log(Level.INFO, "sender is " + clientContext.getName());
        }
        return message;
    }
}
