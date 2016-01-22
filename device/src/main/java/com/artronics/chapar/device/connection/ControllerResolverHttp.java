package com.artronics.chapar.device.connection;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Session;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("controllerResolverHttp")
public class ControllerResolverHttp implements ControllerResolver{
    private final static Logger log = Logger.getLogger(ControllerResolverHttp.class);

    @Override
    public Session connect(String url) {
        return null;
    }

    @Override
    public void sendBuffer(Buffer buffer) {

    }
}
