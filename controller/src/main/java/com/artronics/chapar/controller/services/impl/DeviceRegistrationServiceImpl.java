package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.DeviceRegistrationService;
import com.artronics.chapar.controller.services.NodeRegistrationService;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.exceptions.AddressConflictException;
import com.artronics.chapar.core.repositories.DeviceRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class DeviceRegistrationServiceImpl implements DeviceRegistrationService{
    private final static Logger log = Logger.getLogger(DeviceRegistrationServiceImpl.class);

    private DeviceRepo deviceRepo;

    private Map<Long,Device> registeredDevices;

    private NodeRegistrationService nodeRegistrationService;


    @Override
    public Device registerDevice(Device device) {
        return regDevice(device);
    }

    @Override
    public Device registerDevice(Device device, Long sinkAddress) {
        Device dev = regDevice(device);

        try {
            nodeRegistrationService.registerSink(sinkAddress,dev);

        } catch (AddressConflictException e) {
            e.printStackTrace();
        }

        return dev;
    }


    private Device regDevice(Device device){
        log.debug("Registering new device: "+device);

        deviceRepo.save(device);

        registeredDevices.put(device.getId(),device);

        return device;
    }

    @Resource(name = "registeredDevices")
    public void setRegisteredDevices(Map<Long, Device> registeredDevices) {
        this.registeredDevices = registeredDevices;
    }

    @Autowired
    public void setNodeRegistrationService(NodeRegistrationService nodeRegistrationService) {
        this.nodeRegistrationService = nodeRegistrationService;
    }

    @Autowired
    public void setDeviceRepo(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

}
