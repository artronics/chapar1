package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.factories.PacketFactory;
import com.artronics.chapar.controller.services.PacketService;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PacketServiceImpl implements PacketService{
    private final static Logger log = Logger.getLogger(PacketServiceImpl.class);

    private PacketFactory packetFactory;

    @Override
    public void addPacket(Packet packet) throws MalformedPacketException {
        packet = packetFactory.create(packet);
    }

    public void setPacketFactory(PacketFactory packetFactory) {
        this.packetFactory = packetFactory;
    }
}
