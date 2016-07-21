package com.wavemaker.chatapp.chatserver.model;

/**
 * Created by sainihala on 18/7/16.
 */
public class ClientContext {
    private ClientData clientData;
    private ServerContext serverContext;
    private boolean closed;

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public ClientContext(ClientData clientData, ServerContext serverContext) {
        this.clientData = clientData;
        this.serverContext = serverContext;
    }


    public ServerContext getServerContext() {
        return serverContext;
    }

    public void setServerContext(ServerContext serverContext) {
        this.serverContext = serverContext;
    }

    public ClientData getClientData() {
        return clientData;
    }

    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }
}
