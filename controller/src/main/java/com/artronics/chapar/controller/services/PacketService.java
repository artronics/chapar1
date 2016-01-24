package com.artronics.chapar.controller.services;

import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.MalformedPacketException;

public interface PacketService {
    void addPacket(Packet packet) throws MalformedPacketException;
}
