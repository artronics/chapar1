package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.exceptions.AddressNotRegisteredException;
import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.address.Address;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.repositories.AddressRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AddressRegistrationServiceImpl implements AddressRegistrationService{
    private final static Logger log = Logger.getLogger(AddressRegistrationServiceImpl.class);

    private AddressRepo addressRepo;

    private Map<UnicastAddress,UnicastAddress> unicastAddresses;

    @Override
    public UnicastAddress registerUnicastAddress(Long localAddress, Client client) {
        UnicastAddress ua = UnicastAddress.create(client,localAddress);
        if (isRegistered(ua)){
            return unicastAddresses.get(ua);
        }

        addressRepo.save(ua);
        unicastAddresses.put(ua,ua);

        return ua;
    }

    @Override
    public List<UnicastAddress> resolveAddress(Address address) {
        List<UnicastAddress> resolvedAdds = new ArrayList<>();

        if (address instanceof UnicastAddress){
            if (!unicastAddresses.containsKey(address))
                throw new AddressNotRegisteredException();

            resolvedAdds.add(unicastAddresses.get(address));
        }

        return resolvedAdds;
    }

    @Override
    public boolean isRegistered(Address address) {
        if (address instanceof UnicastAddress){
            return unicastAddresses.containsKey(address);
        }

        return false;
    }

    @Autowired
    public void setAddressRepo(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Resource(name = "unicastAddresses")
    public void setUnicastAddresses(Map<UnicastAddress, UnicastAddress> unicastAddresses) {
        this.unicastAddresses = unicastAddresses;
    }

}
