package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.ClientRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.ClientRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ClientRegistrationServiceImpl implements ClientRegistrationService {
    private final static Logger log = Logger.getLogger(ClientRegistrationServiceImpl.class);

    private ClientRepo clientRepo;

    private Map<Client,Client> registeredClients;

    @Override
    public Client registerDevice(Client client) {
        Client persistedClient = clientRepo.save(client);
        registeredClients.put(persistedClient,persistedClient);

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

    @Resource(name = "registeredClients")
    public void setRegisteredClients(Map<Client, Client> registeredClients) {
        this.registeredClients = registeredClients;
    }

}
