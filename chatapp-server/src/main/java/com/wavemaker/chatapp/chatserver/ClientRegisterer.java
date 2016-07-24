package com.wavemaker.chatapp.chatserver;

import com.wavemaker.chatapp.chatserver.exceptions.ClientAlreadyExistsException;
import com.wavemaker.chatapp.chatserver.model.ClientContext;
import com.wavemaker.chatapp.chatserver.model.ClientData;
import com.wavemaker.chatapp.chatserver.model.ServerContext;
import com.wavemaker.chatapp.commons.exceptions.AppClassNotFoundException;
import com.wavemaker.chatapp.commons.exceptions.AppIOException;
import com.wavemaker.chatapp.commons.ioservices.IOService;
import com.wavemaker.chatapp.commons.messages.RegisterMessage;
import com.wavemaker.chatapp.commons.messages.RegistrationFailed;
import com.wavemaker.chatapp.commons.messages.RegistrationSuccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;


/**
 * Created by sainihala on 20/7/16.
 */
public class ClientRegisterer {

    private static Logger logger = LoggerFactory.getLogger(ClientRegisterer.class.getName());

    public static ClientContext registerClient(Socket socket, ServerContext serverContext, IOService ioService) {
        RegisterMessage registerMessage = null;
        try {
            try {
                registerMessage = (RegisterMessage) ioService.read(socket);
                logger.info("name is {}" , registerMessage.getName());
            } catch (ClassNotFoundException c) {
                throw new AppClassNotFoundException("in client registration ", c);
            }
            try {
                serverContext.getServerRegistry().register(registerMessage.getName(), socket);
            } catch (ClientAlreadyExistsException clientAlreadyExists) {
                ioService.write(socket, new RegistrationFailed("client already exists"));
                throw new ClientAlreadyExistsException("client already exists with name " + registerMessage.getName());
            }
            ioService.write(socket, new RegistrationSuccess(registerMessage.getName()));
            logger.info("{} registered successfully" ,registerMessage.getName());
        } catch (IOException ioe) {
            throw new AppIOException("In ClintRegistration ", ioe);
        }
        ClientContext clientContext = new ClientContext((new ClientData(registerMessage.getName(), socket)),
                serverContext);
        return clientContext;
    }
}
