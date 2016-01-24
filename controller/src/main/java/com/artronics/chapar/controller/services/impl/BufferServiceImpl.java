package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.BufferService;
import com.artronics.chapar.controller.services.PacketService;
import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BufferServiceImpl extends BaseService implements BufferService{
    private final static Logger log = Logger.getLogger(BufferServiceImpl.class);

    private PacketService packetService;

    @Override
    public void addBuffer(Long deviceId, Buffer buffer) throws MalformedPacketException {
        checkDevice(deviceId);

        Packet packet = new Packet(buffer.getContent());

        packetService.addPacket(packet, deviceId);
    }

    @Autowired
    public void setPacketService(PacketService packetService) {
        this.packetService = packetService;
    }

}
