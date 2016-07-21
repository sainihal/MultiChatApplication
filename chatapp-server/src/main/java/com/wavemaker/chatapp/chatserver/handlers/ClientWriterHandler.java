package com.wavemaker.chatapp.chatserver.handlers;

import com.wavemaker.chatapp.chatserver.dao.ServerRegistry;
import com.wavemaker.chatapp.chatserver.model.ClientContext;
import com.wavemaker.chatapp.commons.exceptions.AppIOException;
import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.messages.ChatMessage;
import com.wavemaker.chatapp.commons.messages.ClientNotExists;
import com.wavemaker.chatapp.commons.messages.Message;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by sainihala on 17/7/16.
 */
public class ClientWriterHandler {
    private static Logger logger = Logger.getLogger(ClientReaderHandler.class.getName());

    public static void writeToReceiver(Message message, ClientContext clientContext, IOService ioService) {
        ServerRegistry serverRegistry = clientContext.getServerContext().getServerRegistry();
        String name = clientContext.getClientData().getName();   /* if (clientContext.isClosed()) { return; }*/ //todo
        try {
            if (message.getType() == Message.MessageType.CHAT) {
                ChatMessage chatMessage = (ChatMessage) message;
                logger.log(Level.INFO, "destination is " + chatMessage.getDestination());
                if (serverRegistry.exists(chatMessage.getDestination())) {
                    ioService.write(serverRegistry.getSocket(chatMessage.getDestination()), message);
                } else {
                    message = new ClientNotExists();
                    ioService.write(serverRegistry.getSocket(name), message);
                }
                logger.log(Level.INFO, " message  written to client " + message.toString());
            } else {
                ioService.write(serverRegistry.getSocket(name), message);
            }
        } catch (IOException ioe) {
            clientContext.setClosed(true);
            throw new AppIOException("in worsker of client " + name + "   ", ioe);
        }
    }
}