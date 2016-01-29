package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.entities.Client;
import com.artronics.chapar.controller.repositories.DeviceRepo;
import com.artronics.chapar.controller.services.ClientRegistrationService;
import com.artronics.chapar.domain.map.NetworkMap;
import com.artronics.chapar.domain.map.NetworkMapImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClientRegistrationServiceImpl implements ClientRegistrationService {
    private final static Logger log = Logger.getLogger(ClientRegistrationServiceImpl.class);

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

    @Autowired
    public void setDeviceRepo(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

}
