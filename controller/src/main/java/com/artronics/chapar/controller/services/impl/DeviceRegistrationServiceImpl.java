package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.entities.Client;
import com.artronics.chapar.controller.repositories.DeviceRepo;
import com.artronics.chapar.controller.services.DeviceRegistrationService;
import com.artronics.chapar.domain.map.DeviceMap;
import com.artronics.chapar.domain.map.DeviceMapImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class DeviceRegistrationServiceImpl implements DeviceRegistrationService{
    private final static Logger log = Logger.getLogger(DeviceRegistrationServiceImpl.class);

    private DeviceRepo deviceRepo;

    private Map<Client,DeviceMap> registeredDevices;

    @Override
    public Client registerDevice(Client client) {
        log.debug("Registering new client: "+ client);
        DeviceMap deviceMap = new DeviceMapImpl();

        deviceRepo.save(client);

        registeredDevices.put(client,deviceMap);

        return client;
    }

    @Resource(name = "registeredDevices")
    public void setRegisteredDevices(Map<Client, DeviceMap> registeredDevices) {
        this.registeredDevices = registeredDevices;
    }

    @Autowired
    public void setDeviceRepo(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

}
