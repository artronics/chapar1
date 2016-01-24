package com.artronics.chapar.core.entities;

import com.artronics.chapar.core.entities.packet.PacketType;

import java.util.List;

public interface PacketI {

    List<Integer> getContent();

    Node getSrcNode();

    Node getDstNode();

    PacketType getType();

    Packet.Direction getDirection();

}
