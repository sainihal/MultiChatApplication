package com.wavemaker.chatapp.commons.objectfactory;

import com.wavemaker.chatapp.commons.ioservices.ObjectIOService;

/**
 * Created by sainihala on 19/7/16.
 */
public class ObjectFactory {

    public ObjectFactory() {
    }

    public ObjectIOService getObjectIOService() {
        return new ObjectIOService();
    }
}