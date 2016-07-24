package com.wavemaker.chatpp.chatclient.writer;


import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.messages.Message;
import com.wavemaker.chatapp.commons.messages.QuitMessage;
import com.wavemaker.chatpp.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatpp.chatclient.exceptions.FailedToWriteException;
import com.wavemaker.chatpp.chatclient.io.InputMessageHandler;
import com.wavemaker.chatpp.chatclient.model.ClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Created by sainihala on 27/6/16.
 */
public class ServerWriterWorker implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ServerWriterWorker.class.getName());

    private ClientContext clientContext;
    private InputMessageHandler inputMessageHandler;
    private IOService ioService;

    public ServerWriterWorker(IOService ioService, ClientContext clientContext) {
        this.clientContext = clientContext;
        this.inputMessageHandler = new InputMessageHandler();
        this.ioService = ioService;
    }

    public void run() {
        registerShutdownHook();
        Message message = null;
        try {
            while (!clientContext.isClosed()) {
                message = WriteHandler.readInput(clientContext, inputMessageHandler);
                WriteHandler.writeToServer(message, clientContext, ioService);
            }
        } catch (IOException e) {
            throw new FailedToWriteException("Failed to write to server", e);
        }catch(ClientClosedException clientClosed){
            logger.info(clientClosed.getMessage());
        }
        finally {
            clientContext.setClosed(true);
        }
        logger.info("closing chatclient writer.....");
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                logger.info( "processing in shut down hook");
                if(!clientContext.isClosed()) {
                    try {
                        logger.info( "quitting chat client");
                        WriteHandler.writeToServer(new QuitMessage(clientContext.getName()),
                                clientContext, ioService);
                        logger.info("quit message written");
                    } catch (IOException ioe) {
                        logger.error("quit message not sent", ioe);
                    }
                }
                inputMessageHandler.close();

            }
        });
    }
}

