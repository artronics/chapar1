package com.artronics.chapar.device;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.device.connection.ControllerResolver;
import com.artronics.chapar.device.driver.DeviceDriver;
import com.artronics.chapar.device.events.BufferReadyEvent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeviceInitializer implements ApplicationListener<ContextRefreshedEvent>{
    private final static Logger log = Logger.getLogger(DeviceInitializer.class);

    private String controllerUrl;

    private ControllerResolver controllerResolver;

    private DeviceDriver deviceDriver;

    private Device device;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Device device = new Device();
        try {
            device = controllerResolver.connect(device,controllerUrl);

            if (device != null && device.getId()!=null) {
                controllerResolver.setDeviceId(device.getId());
                this.device = device;

                deviceDriver.init();
                deviceDriver.open();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventListener
    public void bufferReadyHandler(BufferReadyEvent e){
        Buffer b = e.getMessage();
        controllerResolver.sendBuffer(b);
    }

    @Autowired
    @Qualifier("controllerResolverHttp")
    public void setControllerResolver(ControllerResolver controllerResolver) {
        this.controllerResolver = controllerResolver;
    }

    @Autowired
//    @Qualifier("fakeDeviceDriver")
    @Qualifier("deviceDriverSerialPort")
    public void setDeviceDriver(DeviceDriver deviceDriver) {
        this.deviceDriver = deviceDriver;
    }

    @Value("${com.artronics.chapar.device.controller.url}")
    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }
}
