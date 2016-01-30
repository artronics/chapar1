package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.ClientRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.ClientRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientRegistrationServiceImpl implements ClientRegistrationService {
    private final static Logger log = Logger.getLogger(ClientRegistrationServiceImpl.class);

    private ClientRepo clientRepo;

    @Override
    public Client registerDevice(Client client) {
        Client persistedClient = clientRepo.save(client);

        log.debug("New Client has been registered. ID: "+persistedClient.getId());

        return persistedClient;
    }

    @Override
    public Client registerDevice(Client client, Long sinkAddress) {
        return registerDevice(client);
    }

    @Autowired
    public void setClientRepo(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

}
