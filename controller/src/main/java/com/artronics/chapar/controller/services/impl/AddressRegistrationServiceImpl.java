package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.Set;

public class AddressRegistrationServiceImpl implements AddressRegistrationService{
    private final static Logger log = Logger.getLogger(AddressRegistrationServiceImpl.class);

    private Set<UnicastAddress> unicastAddresses;

    @Override
    public UnicastAddress registerUnicastAddress(Long localAddress, Client client) {
        return null;
    }

    @Resource(name = "unicastAddresses")
    public void setUnicastAddresses(Set<UnicastAddress> unicastAddresses) {
        this.unicastAddresses = unicastAddresses;
    }
}
