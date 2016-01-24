package com.artronics.chapar.controller.factories;

import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.MalformedPacketException;

public interface PacketFactory {
    <T extends Packet> T create(Packet packet) throws MalformedPacketException;
}
