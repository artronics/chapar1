package com.artronics.chapar.controller.remote;

import com.artronics.chapar.core.entities.Session;
import com.artronics.chapar.core.remote.RemoteControllerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class RemoteControllerServiceImpl implements RemoteControllerService{
    private final static Logger log = Logger.getLogger(RemoteControllerServiceImpl.class);

    @Override
    public Session connect(String controllerUrl) {
        return new Session();
    }

    @Override
    public void registerDevice() {
        log.debug("Registering new Device");
    }
}
