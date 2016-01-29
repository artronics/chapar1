package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.entities.Client;
import com.artronics.chapar.controller.repositories.DeviceRepo;
import com.artronics.chapar.controller.services.DeviceRegistrationService;
import com.artronics.chapar.domain.map.NetworkMap;
import com.artronics.chapar.domain.map.NetworkMapImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class DeviceRegistrationServiceImpl implements DeviceRegistrationService{
    private final static Logger log = Logger.getLogger(DeviceRegistrationServiceImpl.class);

    private DeviceRepo deviceRepo;

    private Map<Client,NetworkMap> registeredDevices;

    @Override
    public Client registerDevice(Client client) {
        log.debug("Registering new client: "+ client);
        NetworkMap networkMap = new NetworkMapImpl();

        deviceRepo.save(client);

        registeredDevices.put(client, networkMap);

        return client;
    }

    @Resource(name = "registeredDevices")
    public void setRegisteredDevices(Map<Client, NetworkMap> registeredDevices) {
        this.registeredDevices = registeredDevices;
    }

    @Autowired
    public void setDeviceRepo(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

}
