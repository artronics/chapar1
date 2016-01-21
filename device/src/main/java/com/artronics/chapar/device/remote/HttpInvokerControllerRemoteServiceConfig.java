package com.artronics.chapar.device.remote;

import com.artronics.chapar.core.remote.RemoteControllerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class HttpInvokerControllerRemoteServiceConfig {
    private final static Logger log = Logger.getLogger(HttpInvokerControllerRemoteServiceConfig.class);

    private String controllerUrl;

    @Bean(name = "httpInvokerControllerRemoteService")
    public RemoteControllerService getRemoteControllerService() {
        HttpInvokerProxyFactoryBean pb = new HttpInvokerProxyFactoryBean();
        pb.setServiceUrl(controllerUrl+"/rmi/controller");
        pb.setServiceInterface(RemoteControllerService.class);
        pb.afterPropertiesSet();
        RemoteControllerService s = (RemoteControllerService) pb.getObject();
        log.debug("Fetching Remote Service Bean: " + s.toString());

        return s;
    }

    @Value("${com.artronics.chapar.device.controller.url}")
    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }
}
