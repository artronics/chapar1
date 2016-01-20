package com.artronics.chapar.controller.remote;

import com.artronics.chapar.core.remote.RemoteControllerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.stereotype.Component;

@Component
public class HessianServiceExporterConfig {
    private final static Logger log = Logger.getLogger(HessianServiceExporterConfig.class);

    private RemoteControllerService remoteControllerService;

    @Bean(name = "/controller")
    public HessianServiceExporter sdwnControllerServiceExport() {
        HessianServiceExporter he = new HessianServiceExporter();
        log.debug("Creating Hessian service exporter for: "+remoteControllerService.toString());
        he.setService(remoteControllerService);
        he.setServiceInterface(RemoteControllerService.class);
        return he;
    }

    @Autowired
    public void setRemoteControllerService(RemoteControllerService remoteControllerService) {
        this.remoteControllerService = remoteControllerService;
    }
}
