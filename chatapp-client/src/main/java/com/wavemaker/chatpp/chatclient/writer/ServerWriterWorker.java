package com.wavemaker.chatpp.chatclient.writer;


import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.messages.Message;
import com.wavemaker.chatapp.commons.messages.QuitMessage;
import com.wavemaker.chatpp.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatpp.chatclient.exceptions.FailedToWriteException;
import com.wavemaker.chatpp.chatclient.io.MessageReader;
import com.wavemaker.chatpp.chatclient.model.ClientContext;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sainihala on 27/6/16.
 */
public class ServerWriterWorker implements Runnable {
    private static final Logger logger = Logger.getLogger(ServerWriterWorker.class.getName());

    private ClientContext clientContext;
    private MessageReader messageReader;
    private boolean quitted;
    private IOService ioService;

    public ServerWriterWorker(IOService ioService, ClientContext clientContext, MessageReader messageReader) {
        this.clientContext = clientContext;
        this.messageReader = messageReader;
        this.ioService = ioService;
    }

    public void run() {
        registerShutdownHook();
        Message message;
        try {
            while (!clientContext.isClosed()) {
                message = WriteHandler.readInput(clientContext, messageReader);
                if (message.getType() == Message.MessageType.QUIT) {
                    quitted = true;
                }
                WriteHandler.writeToServer(message, clientContext.getSocket(), ioService);
            }
        } catch (ClientClosedException clientClosed) {
            logger.log(Level.INFO, "Client Closed ");
        } catch (IOException e) {
            throw new FailedToWriteException("Failed to write to server", e);
        } finally {
            clientContext.setClosed(true);
        }
        logger.log(Level.INFO, "closing chatclient writer.....");
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                logger.log(Level.INFO, "processing in shut down hook");
                if (!quitted) {
                    try {
                        logger.log(Level.INFO, "quitting chat client");
                        WriteHandler.writeToServer(new QuitMessage(clientContext.getName()),
                                clientContext.getSocket(), ioService);
                        logger.log(Level.INFO, "quit message written");
                        messageReader.close();
                    } catch (IOException ioe) {
                        logger.log(Level.SEVERE, "quit message not sent", ioe);
                    }
                    clientContext.setClosed(true);
                }
            }
        });
    }
}

