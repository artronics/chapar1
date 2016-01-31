package com.artronics.chapar.controller.entities.packet;


import com.artronics.chapar.controller.exceptions.MalformedPacketException;
import com.artronics.chapar.domain.entities.Buffer;

public interface PacketFactory {
    <T extends Packet> T create(Buffer buffer) throws MalformedPacketException;
}
