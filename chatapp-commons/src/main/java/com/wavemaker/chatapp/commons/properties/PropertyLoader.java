package com.wavemaker.chatapp.commons.properties;


import com.wavemaker.chatapp.commons.exceptions.AppIOException;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by sainihala on 16/7/16.
 */
public class PropertyLoader {
    public static int getPort() {
        {
            Properties properties = new Properties();
            try {
                properties.load(PropertyLoader.class.getResourceAsStream("/config.properties"));
            } catch (IOException e) {
                throw new AppIOException("In loading properties", e);
            }
            return Integer.parseInt(properties.getProperty(Constants.PORT));
        }
    }
}