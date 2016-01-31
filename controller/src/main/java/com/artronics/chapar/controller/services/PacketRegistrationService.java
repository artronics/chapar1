package com.artronics.chapar.controller.services;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.domain.entities.Client;

public interface PacketRegistrationService {
    Packet registerPacket(Packet packet, Client client);
}
