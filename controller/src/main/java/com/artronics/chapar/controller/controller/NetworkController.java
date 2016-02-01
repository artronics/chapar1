package com.artronics.chapar.controller.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.entities.packet.PacketType;

public interface NetworkController<T extends Enum<T> & PacketType> {
    void start();

    Packet<T> processePacket(Packet<T> packet);
}
