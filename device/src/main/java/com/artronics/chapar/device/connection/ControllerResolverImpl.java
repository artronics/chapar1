package com.artronics.chapar.device.connection;

import com.artronics.chapar.core.remote.RemoteControllerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ControllerResolverImpl implements ControllerResolver{
    private final static Logger log = Logger.getLogger(ControllerResolverImpl.class);

    private RemoteControllerService remoteControllerService;

    @Override
    public void connect(String url) {
        log.debug("Connecting to controller");
        remoteControllerService.connect(url);
    }

    @Autowired
    @Qualifier("hessianRemoteControllerService")
    public void setRemoteControllerService(RemoteControllerService remoteControllerService) {
        this.remoteControllerService = remoteControllerService;
    }
}
