package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.exceptions.DeviceNotRegistered;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.Set;

public class BaseService {
    private final static Logger log = Logger.getLogger(BaseService.class);

    protected Set<Device> registeredDevices;

    protected void checkDevice(Long deviceId){
        Device device = new Device(deviceId);
        checkDevice(device);
    }

    protected void checkDevice(Device device){
        if (!registeredDevices.contains(device))
            throw new DeviceNotRegistered();
    }

    @Resource(name = "registeredDevices")
    public void setRegisteredDevices(Set<Device> registeredDevices) {
        this.registeredDevices = registeredDevices;
    }

}
