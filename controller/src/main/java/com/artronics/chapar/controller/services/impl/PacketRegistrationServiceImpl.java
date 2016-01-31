package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.services.PacketRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PacketRegistrationServiceImpl implements PacketRegistrationService{
    private final static Logger log = Logger.getLogger(PacketRegistrationServiceImpl.class);

    @Override
    public Packet registerPacket(Packet packet, Client client) {
        return packet;
    }
}
