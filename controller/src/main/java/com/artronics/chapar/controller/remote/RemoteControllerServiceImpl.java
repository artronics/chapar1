package com.artronics.chapar.controller.remote;

import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Session;
import com.artronics.chapar.core.remote.RemoteControllerService;
import com.artronics.chapar.core.remote.RemoteDeviceService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class RemoteControllerServiceImpl implements RemoteControllerService{
    private final static Logger log = Logger.getLogger(RemoteControllerServiceImpl.class);

    @Override
    public Session connect() {
        return new Session();
    }

    @Override
    public Device registerDeviceService(Device device, RemoteDeviceService remoteDeviceServiceProxy) {
        log.debug("Registering new Device");

        return device;
    }
}
