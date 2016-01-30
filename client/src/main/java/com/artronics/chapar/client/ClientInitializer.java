package com.artronics.chapar.client;

import com.artronics.chapar.client.driver.DeviceDriver;
import com.artronics.chapar.client.events.BufferReadyEvent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ClientInitializer implements ApplicationListener<ContextRefreshedEvent>{
    private final static Logger log = Logger.getLogger(ClientInitializer.class);

    private String controllerUrl;

    private DeviceDriver deviceDriver;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        deviceDriver.init();
        deviceDriver.open();


    }

    @EventListener
    public void bufferReadyHandler(BufferReadyEvent e){
    }

    @Autowired
    @Qualifier("fakeDeviceDriver")
    public void setDeviceDriver(DeviceDriver deviceDriver) {
        this.deviceDriver = deviceDriver;
    }

    @Value("${com.artronics.chapar.client.controller.url}")
    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }
}
