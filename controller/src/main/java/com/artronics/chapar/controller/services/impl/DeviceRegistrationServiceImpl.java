package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.DeviceRegistrationService;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.map.DeviceMap;
import com.artronics.chapar.core.map.DeviceMapImpl;
import com.artronics.chapar.core.repositories.DeviceRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceRegistrationServiceImpl extends BaseService implements DeviceRegistrationService{
    private final static Logger log = Logger.getLogger(DeviceRegistrationServiceImpl.class);

    private DeviceRepo deviceRepo;

    @Override
    public Device registerDevice(Device device) {
        log.debug("Registering new device: "+device);
        DeviceMap deviceMap = new DeviceMapImpl();

        deviceRepo.save(device);

        registeredDevices.put(device,deviceMap);

        return device;
    }

    @Autowired
    public void setDeviceRepo(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

}
