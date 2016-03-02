package com.artronics.chapar.controller.services;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.exceptions.MalformedPacketException;

public interface PacketService{
    void checkForRxBuffers();

    Packet receivePacket(Packet packet) throws MalformedPacketException;

}
