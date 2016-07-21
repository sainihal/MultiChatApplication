package com.wavemaker.chatpp.chatclient.reader;


import com.wavemaker.chatpp.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatpp.chatclient.exceptions.FailedToReadException;
import com.wavemaker.chatpp.chatclient.model.ClientContext;
import com.wavemaker.chatapp.commons.ioservices.IOService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sainihala on 27/6/16.
 */
public class ServerReaderWorker implements Runnable {
    private static final Logger logger = Logger.getLogger(ServerReaderWorker.class.getName());
    private ClientContext clientContext;
    private IOService ioService;

    public ServerReaderWorker(IOService ioService, ClientContext clientContext) {
        this.ioService = ioService;
        this.clientContext = clientContext;
    }

    public void run() {
        try {
            while (!clientContext.isClosed()) {
                ReadHandler.readFromServer(clientContext.getSocket(), clientContext, ioService);
            }
        } catch (ClientClosedException clientClosed) {
            logger.log(Level.INFO, "Client closed");
        } catch (Exception e) {
            throw new FailedToReadException("failed to read from server", e);
        } finally {
            clientContext.setClosed(true);
        }
        logger.log(Level.INFO, "closing chatclient reader....");
    }
}



