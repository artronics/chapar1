package com.artronics.chapar.controller.services;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.address.Address;
import com.artronics.chapar.domain.entities.address.UnicastAddress;

public interface AddressRegistrationService {
    UnicastAddress registerUnicastAddress(Long localAddress, Client client);

    boolean isRegistered(Address address);
}
