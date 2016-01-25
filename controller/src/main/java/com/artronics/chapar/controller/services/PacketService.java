package com.artronics.chapar.controller.services;

import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;

public interface PacketService {
    void addPacket(Packet packet,Long deviceId) throws MalformedPacketException, NodeNotRegistered;
}
