package com.artronics.chapar.controller.services;

import com.artronics.chapar.domain.entities.Client;

public interface DeviceRegistrationService {
    Client registerDevice(Client client);
}
