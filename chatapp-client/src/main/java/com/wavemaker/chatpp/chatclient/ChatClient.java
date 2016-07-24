package com.wavemaker.chatpp.chatclient;

/**
 * Created by sainihala on 23/6/16.
 */


import com.wavemaker.chatapp.commons.exceptions.AppClassNotFoundException;
import com.wavemaker.chatapp.commons.exceptions.AppIOException;
import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.messages.Message;
import com.wavemaker.chatapp.commons.messages.RegisterMessage;
import com.wavemaker.chatapp.commons.objectfactory.ObjectFactory;
import com.wavemaker.chatpp.chatclient.exceptions.AppInterruptedException;
import com.wavemaker.chatpp.chatclient.exceptions.RegistrationFailedException;
import com.wavemaker.chatpp.chatclient.model.ClientContext;
import com.wavemaker.chatpp.chatclient.reader.ServerReaderWorker;
import com.wavemaker.chatpp.chatclient.writer.ServerWriterWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;



public class ChatClient {
    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class.getName());

    private ClientContext clientContext;
    private ObjectFactory objectFactory;
    private IOService ioService;

    public ChatClient(String host, int port, String name) {
        clientContext = new ClientContext();
        try {
            clientContext.setSocket((new Socket(host, port)));
        } catch (IOException ioe) {
            throw new AppIOException("In creating socket", ioe);
        }
        clientContext.setName(name);
        this.objectFactory = new ObjectFactory();
        this.ioService = objectFactory.getObjectIOService();
        registerClient();
    }

    public void start() {
        Thread serverWriterWorker = new Thread(new ServerWriterWorker(ioService, clientContext));
        Thread serverReaderWorker = new Thread(new ServerReaderWorker(ioService, clientContext));
        serverReaderWorker.start();
        serverWriterWorker.start();
        try {
            serverWriterWorker.join();
            serverReaderWorker.join();
        } catch (InterruptedException interruptedException) {
            throw new AppInterruptedException("In chatclient", interruptedException);
        } finally {
            closeSocket();
        }
    }

    private void registerClient() {
        RegisterMessage registerMessage;
        Message acknowledgementMessage;

        try {
            logger.info("Client Registering...");
            registerMessage = new RegisterMessage(clientContext.getName());
            ioService.write(clientContext.getSocket(), registerMessage);
            acknowledgementMessage = ioService.read(clientContext.getSocket());
            if (acknowledgementMessage.getType() == Message.MessageType.REGISTRATION_SUCCESS) {
                logger.info(acknowledgementMessage.toString());
            } else if (acknowledgementMessage.getType() == Message.MessageType.REGISTRATION_FAILED) {
                logger.info("Registration Failed, {}" , acknowledgementMessage);
                closeSocket();
                throw new RegistrationFailedException(acknowledgementMessage.toString());
            }
        } catch (IOException ioe) {
            closeSocket();
            throw new AppIOException("In chatclient init ", ioe);
        } catch (ClassNotFoundException classNotFound) {
            closeSocket();
            throw new AppClassNotFoundException("In  chatclient init", classNotFound);
        }
    }

    private void closeSocket() {
        logger.info("Closing socket");
        try {
            clientContext.getSocket().close();
        } catch (IOException e) {
            throw new AppIOException("In closing socket");
        }
    }
}
