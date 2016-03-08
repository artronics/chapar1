package com.artronics.chapar.controller.services;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.exceptions.MalformedPacketException;
import com.artronics.chapar.domain.entities.Buffer;

public interface PacketService{
    void checkForRxBuffers();

    Packet receivePacket(Packet packet) throws MalformedPacketException;

    void receiveBuffer(Buffer buffer);

    Buffer receiveBufferAndGetResponse(Buffer buffer);

}
