package com.artronics.chapar.controller.services;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.address.Address;
import com.artronics.chapar.domain.entities.address.UnicastAddress;

import java.util.List;

public interface AddressRegistrationService {
    UnicastAddress registerUnicastAddress(Long localAddress, Client client);

    List<UnicastAddress> resolveAddress(Address address);

    boolean isRegistered(Address address);
}
