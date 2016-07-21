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
import com.wavemaker.chatpp.chatclient.exceptions.RegistrataionFailed;
import com.wavemaker.chatpp.chatclient.io.MessageReader;
import com.wavemaker.chatpp.chatclient.model.ClientContext;
import com.wavemaker.chatpp.chatclient.reader.ServerReaderWorker;
import com.wavemaker.chatpp.chatclient.writer.ServerWriterWorker;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatClient {
    private static final Logger logger = Logger.getLogger(ChatClient.class.getName());

    private ClientContext clientContext;
    private MessageReader messageReader;
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
        messageReader = (messageReader == null) ? new MessageReader() : messageReader;

        Thread serverWriterWorker = new Thread(new ServerWriterWorker(ioService, clientContext, messageReader));
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

    public void start(String fileName) {
        this.messageReader = new MessageReader(fileName);
        start();
    }

    private void registerClient() {
        RegisterMessage registerMessage;
        Message acknowledgementMessage;

        try {
            logger.log(Level.INFO, "Client Registering...");
            registerMessage = new RegisterMessage(clientContext.getName());
            ioService.write(clientContext.getSocket(), registerMessage);
            acknowledgementMessage = ioService.read(clientContext.getSocket());
            if (acknowledgementMessage.getType() == Message.MessageType.REGISTRATION_SUCCESS) {
                logger.log(Level.INFO, "Registration Success " + acknowledgementMessage.toString());
            } else if (acknowledgementMessage.getType() == Message.MessageType.REGISTRATION_FAILED) {
                logger.log(Level.INFO, "Registration Failed " + acknowledgementMessage.toString());
                closeSocket();
                throw new RegistrataionFailed(acknowledgementMessage.toString());
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
        logger.log(Level.INFO, "Closing socket..");
        try {
            clientContext.getSocket().close();
        } catch (IOException e) {
            throw new AppIOException("In closing socket");
        }
    }
}
