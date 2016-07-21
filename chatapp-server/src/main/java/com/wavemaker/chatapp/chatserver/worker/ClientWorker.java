package com.wavemaker.chatapp.chatserver.worker;

/**
 * Created by sainihala on 23/6/16.
 */


import com.wavemaker.chatapp.chatserver.handlers.ClientReaderHandler;
import com.wavemaker.chatapp.chatserver.handlers.ClientWriterHandler;
import com.wavemaker.chatapp.chatserver.model.ClientContext;
import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.messages.Message;


/**
 * Created by sainihala on 23/6/16.
 */
public class ClientWorker implements Runnable {

    private ClientContext clientContext;
    private IOService service;

    public ClientWorker(ClientContext clientContext, IOService ioService) {
        this.clientContext = clientContext;
        this.service = ioService;
    }

    public void run() {
        while (!clientContext.isClosed() && !clientContext.getServerContext().isClosed()) {
            Message message = ClientReaderHandler.readFromSender(clientContext, service);
            if (message.getType() == Message.MessageType.QUIT) {
                clientContext.setClosed(true);
                clientContext.getServerContext().getServerRegistry().deRegister(clientContext.getClientData().getName());
                break;
            }
            ClientWriterHandler.writeToReceiver(message, clientContext, service);
        }
    }
}



