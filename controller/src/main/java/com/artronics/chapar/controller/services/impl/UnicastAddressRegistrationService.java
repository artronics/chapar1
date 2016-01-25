package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Link;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class UnicastAddressRegistrationService implements AddressRegistrationService {
    private final static Logger log = Logger.getLogger(UnicastAddressRegistrationService.class);

    private Set<Address> unicastAddressSpace;

    @Override
    public Address registerSinkAddress(Long localAddress, Long deviceId) {
        return null;
    }

    @Override
    public void registerSrcDstAddressInPacket(Address srcAdd, Address dstAdd) {
        if (!unicastAddressSpace.contains(srcAdd)){
            log.debug("Registering new Source Address: "+srcAdd);
            unicastAddressSpace.add(srcAdd);
        }
        if (!unicastAddressSpace.contains(dstAdd)){
            log.debug("Registering new Destination Address: "+dstAdd);
            unicastAddressSpace.add(dstAdd);
        }
    }

    @Override
    public Set<Link> registerNeighborsAddress(Set<Link> links) {
        links.forEach(link -> {
            Address address = link.getDstNode().getAddress();
            if (!unicastAddressSpace.contains(address)){
                log.debug("Registering Neighbor Address "+address);
                unicastAddressSpace.add(address);
            }
        });

        return links;
    }

    @Resource(name = "unicastAddressSpace")
    public void setUnicastAddressSpace(Set<Address> unicastAddressSpace) {
        this.unicastAddressSpace = unicastAddressSpace;
    }
}
