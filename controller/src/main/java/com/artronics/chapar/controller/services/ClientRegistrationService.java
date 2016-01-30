package com.artronics.chapar.controller.services;

import com.artronics.chapar.domain.entities.Client;

public interface ClientRegistrationService {
    Client registerDevice(Client client);

    Client registerDevice(Client client, Long sinkAddress);
}
