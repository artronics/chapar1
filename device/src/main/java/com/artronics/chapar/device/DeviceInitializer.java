package com.artronics.chapar.device;

import com.artronics.chapar.device.connection.ControllerResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DeviceInitializer implements ApplicationListener<ContextRefreshedEvent>{
    private final static Logger log = Logger.getLogger(DeviceInitializer.class);

    private String controllerUrl;

    private ControllerResolver controllerResolver;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        controllerResolver.connect(controllerUrl);
    }

    @Autowired
    public void setControllerResolver(ControllerResolver controllerResolver) {
        this.controllerResolver = controllerResolver;
    }

    @Value("${com.artronics.chapar.device.controller.url}")
    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }
}
