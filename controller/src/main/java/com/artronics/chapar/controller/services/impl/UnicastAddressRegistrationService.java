package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.core.entities.Address;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class UnicastAddressRegistrationService implements AddressRegistrationService {
    private final static Logger log = Logger.getLogger(UnicastAddressRegistrationService.class);

    private Set<Address> unicastAddressSpace;

    @Override
    public void resolveAddress(Address srcAdd, Address dstAdd) {
        if (!unicastAddressSpace.contains(srcAdd)){
            log.debug("Registering new Address: "+srcAdd);
            unicastAddressSpace.add(srcAdd);
        }
        if (!unicastAddressSpace.contains(dstAdd)){
            log.debug("Registering new Address: "+dstAdd);
            unicastAddressSpace.add(dstAdd);
        }
    }

    @Resource(name = "unicastAddressSpace")
    public void setUnicastAddressSpace(Set<Address> unicastAddressSpace) {
        this.unicastAddressSpace = unicastAddressSpace;
    }
}
