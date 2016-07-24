package com.wavemaker.chatapp.chatserver.handlers;

import com.wavemaker.chatapp.chatserver.model.ClientContext;
import com.wavemaker.chatapp.commons.exceptions.AppClassNotFoundException;
import com.wavemaker.chatapp.commons.exceptions.AppIOException;
import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.messages.Message;
import com.wavemaker.chatapp.commons.messages.ServerExiting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

;

/**
 * Created by sainihala on 17/7/16.
 */
public class ClientReaderHandler {

    private static Logger logger = LoggerFactory.getLogger(ClientReaderHandler.class.getName());


    public static Message readFromSender(ClientContext clientContext, IOService ioService) {
        Message message;
        try {
            logger.info("Server: reading from client.......{}" , clientContext.getClientData().getName());
            while (true) {
                if (clientContext.getClientData().getSocket().getInputStream().available() != 0) {
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
        logger.info(message.toString());
        return message;
    }
}
