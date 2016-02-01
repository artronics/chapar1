package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.controller.services.PacketRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacketRegistrationServiceImpl implements PacketRegistrationService{
    private final static Logger log = Logger.getLogger(PacketRegistrationServiceImpl.class);

    private AddressRegistrationService addressRegistrationService;

    @Override
    public Packet registerPacket(Packet packet, Client client) {
        checkNull(packet);

        registerSrcAndDstAddresses(packet, client);

        return packet;
    }

    private void registerSrcAndDstAddresses(Packet packet, Client client) {
        //Source address is always a unicast address
        Long srcAdd = packet.getSrcLocalAddress();
        UnicastAddress srcUa = UnicastAddress.create(client,srcAdd);
        if (!addressRegistrationService.isRegistered(srcUa)){
            srcUa=addressRegistrationService.registerUnicastAddress(srcAdd,client);
            packet.setSrcAddress(srcUa);
        }

        //Destination address could be either Unicast or Multicast but
        //for new we just support unicast address
        Long dstAdd = packet.getDstLocalAddress();
        UnicastAddress dstUa = UnicastAddress.create(client,dstAdd);
        if (!addressRegistrationService.isRegistered(dstUa)){
            dstUa = addressRegistrationService.registerUnicastAddress(dstAdd,client);
            packet.setDstAddress(dstUa);
        }
    }

    private void checkNull(Packet packet){
        if (packet.getSrcAddress()==null)
            assert packet.getSrcLocalAddress()!=null;

        if (packet.getDstAddress()==null)
            assert packet.getDstLocalAddress()!=null;
    }

    @Autowired
    public void setAddressRegistrationService(AddressRegistrationService addressRegistrationService) {
        this.addressRegistrationService = addressRegistrationService;
    }

}
