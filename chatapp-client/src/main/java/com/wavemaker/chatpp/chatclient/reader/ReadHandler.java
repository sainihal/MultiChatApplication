package com.wavemaker.chatpp.chatclient.reader;

import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.messages.Message;
import com.wavemaker.chatpp.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatpp.chatclient.exceptions.ServerClosedException;
import com.wavemaker.chatpp.chatclient.model.ClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;



/**
 * Created by sainihala on 14/7/16.
 */
public class ReadHandler {
    private static final Logger logger = LoggerFactory.getLogger(ReadHandler.class.getName());

    public static void readFromServer(ClientContext clientContext, IOService ioService)
            throws IOException, ClassNotFoundException, ClientClosedException,ServerClosedException {
        while (true) {
            if (clientContext.getSocket().getInputStream().available() != 0) {
                break;
            }
            if (clientContext.isClosed()) {
                throw new ClientClosedException("client closed");
            }
        }
        Message readObject = ioService.read(clientContext.getSocket());
        logger.info("Client:   data read in chatclient is........{}" ,readObject.toString());
        if (readObject.getType() == Message.MessageType.SERVER_EXITING) {
            throw new ServerClosedException("Server closed");
        }
    }
}
