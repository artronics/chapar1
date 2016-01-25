package com.artronics.chapar.controller.services;

import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.entities.packet.PacketType;
import com.artronics.chapar.core.exceptions.MalformedPacketException;

public interface NetworkController <T extends Enum<T> & PacketType,S extends Packet<T>>{

    Packet<T> processPacket(S packet) throws MalformedPacketException;
}
