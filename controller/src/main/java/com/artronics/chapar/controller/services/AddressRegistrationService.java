package com.artronics.chapar.controller.services;

import com.artronics.chapar.core.entities.Address;

public interface AddressRegistrationService {
    void resolveAddress(Address srcAdd, Address dstAdd);
}
