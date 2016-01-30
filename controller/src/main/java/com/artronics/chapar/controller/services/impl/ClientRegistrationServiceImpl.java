package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.entities.Client;
import com.artronics.chapar.controller.repositories.ClientRepo;
import com.artronics.chapar.controller.services.ClientRegistrationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientRegistrationServiceImpl implements ClientRegistrationService {
    private final static Logger log = Logger.getLogger(ClientRegistrationServiceImpl.class);

    private ClientRepo clientRepo;

    @Override
    public Client registerDevice(Client client) {
        log.debug("Registering new client: "+ client);

        clientRepo.save(client);

        return client;
    }

    @Override
    public Client registerDevice(Client client, Long sinkAddress) {
        return null;
    }

    @Autowired
    public void setClientRepo(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

}
