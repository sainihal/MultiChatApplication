package com.wavemaker.chatapp.chatserver.handlers;

import com.wavemaker.chatapp.chatserver.model.ClientContext;
import com.wavemaker.chatapp.commons.exceptions.AppClassNotFoundException;
import com.wavemaker.chatapp.commons.exceptions.AppIOException;
import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.messages.Message;
import com.wavemaker.chatapp.commons.messages.ServerExiting;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

;

/**
 * Created by sainihala on 17/7/16.
 */
public class ClientReaderHandler {

    private static Logger logger = Logger.getLogger(ClientReaderHandler.class.getName());


    public static Message readFromSender(ClientContext clientContext, IOService ioService) {
        Message message;
        try {
            logger.log(Level.INFO, "Server: reading from client......." + clientContext.getClientData().getName());
            while (true) {
                if (clientContext.getClientData().getSocket().getInputStream().available() != 0) { //todo
                    break;
                }
                if (clientContext.getServerContext().isClosed()) {
                    message = new ServerExiting();
                    return message;
                }
            }
            try {
                message = ioService.read(clientContext.getClientData().getSocket());
            } catch (ClassNotFoundException classNotFound) {
                clientContext.setClosed(true);
                throw new AppClassNotFoundException("in client " + clientContext.getClientData().getName(), classNotFound);
            }
        } catch (IOException ioe) {
            clientContext.setClosed(true);
            throw new AppIOException("in reading from client " + clientContext.getClientData().getName(), ioe);
        }
        logger.log(Level.INFO, message.toString());
        return message;
    }
}
