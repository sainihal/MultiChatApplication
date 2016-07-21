package com.wavemaker.chatpp.chatclient.reader;

import com.wavemaker.chatpp.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatpp.chatclient.model.ClientContext;
import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.messages.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

;

/**
 * Created by sainihala on 14/7/16.
 */
public class ReadHandler {
    private static final Logger logger = Logger.getLogger(ReadHandler.class.getName());

    public static void readFromServer(Socket socket, ClientContext clientContext, IOService ioService)
            throws IOException, ClassNotFoundException, ClientClosedException {
        Message readObject;

        while (true) {
            if (socket.getInputStream().available() != 0) {
                break;
            }
            if (clientContext.isClosed()) {
                throw new ClientClosedException("chatclient closed");
            }
        }
        readObject = ioService.read(socket);
        logger.log(Level.INFO, "Client:   data read in chatclient is........" + readObject.toString());
        if (readObject.getType() == Message.MessageType.SERVER_EXITING) {
            clientContext.setClosed(true);
        }
    }
}
