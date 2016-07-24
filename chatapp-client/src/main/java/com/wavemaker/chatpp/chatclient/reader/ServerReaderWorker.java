package com.wavemaker.chatpp.chatclient.reader;


import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatpp.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatpp.chatclient.exceptions.FailedToReadException;
import com.wavemaker.chatpp.chatclient.exceptions.ServerClosedException;
import com.wavemaker.chatpp.chatclient.model.ClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sainihala on 27/6/16.
 */
public class ServerReaderWorker implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ServerReaderWorker.class.getName());
    private ClientContext clientContext;
    private IOService ioService;

    public ServerReaderWorker(IOService ioService, ClientContext clientContext) {
        this.ioService = ioService;
        this.clientContext = clientContext;
    }

    public void run() {
        try {
            while (!clientContext.isClosed()) {
                ReadHandler.readFromServer(clientContext, ioService);
            }
        } catch (ClientClosedException message) {
            logger.info(message.getMessage());
        } catch (ServerClosedException message) {
            logger.info(message.getMessage());
        } catch (Exception e) {
            throw new FailedToReadException("failed to read from server", e);
        } finally {
            clientContext.setClosed(true);
        }
        logger.info("closing chatclient reader....");
    }
}



