package com.artronics.chapar.controller.services;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Link;

import java.util.Set;

public interface AddressRegistrationService {
    void resolveAddress(Address srcAdd, Address dstAdd);

    Set<Link> registerNeighborsAddress(Set<Link> links);
}
