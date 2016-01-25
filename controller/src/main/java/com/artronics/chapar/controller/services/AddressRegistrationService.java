package com.artronics.chapar.controller.services;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.exceptions.AddressConflictException;

import java.util.Set;

public interface AddressRegistrationService {
    Address registerSinkAddress(Long localAddress, Device device) throws AddressConflictException;

    void registerSrcDstAddressInPacket(Address srcAdd, Address dstAdd);

    Set<Link> registerNeighborsAddress(Set<Link> links);
}
