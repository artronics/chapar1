package com.artronics.chapar.device.remote;

import com.artronics.chapar.core.remote.RemoteControllerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class HessianControllerRemoteServiceConfig {
    private final static Logger log = Logger.getLogger(HessianControllerRemoteServiceConfig.class);

    private String controllerUrl;

    @Bean(name = "hessianRemoteControllerService")
    public RemoteControllerService getRemoteControllerService() {
        HessianProxyFactoryBean pb = new HessianProxyFactoryBean();
        pb.setServiceUrl(controllerUrl+"/controller");
        pb.setServiceInterface(RemoteControllerService.class);
        pb.afterPropertiesSet();
        RemoteControllerService s = (RemoteControllerService) pb.getObject();
        log.debug("Fetching Remote Service Bean: " + s.toString());

        return s;
    }

    @Value("${com.artronics.chapar.controller.url}")
    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }
}
