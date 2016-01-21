package com.artronics.chapar.device;

import com.artronics.chapar.device.connection.ControllerResolver;
import com.artronics.chapar.device.driver.DeviceDriver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DeviceInitializer implements ApplicationListener<ContextRefreshedEvent>{
    private final static Logger log = Logger.getLogger(DeviceInitializer.class);

    private String controllerUrl;

    private ControllerResolver controllerResolver;

    private DeviceDriver deviceDriver;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        controllerResolver.connect(controllerUrl);
        deviceDriver.init();
        deviceDriver.open();
    }

    @Autowired
    public void setControllerResolver(ControllerResolver controllerResolver) {
        this.controllerResolver = controllerResolver;
    }

    @Autowired
    @Qualifier("fakeDeviceDriver")
    public void setDeviceDriver(DeviceDriver deviceDriver) {
        this.deviceDriver = deviceDriver;
    }

    @Value("${com.artronics.chapar.device.controller.url}")
    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }
}
