package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DataPacketProcessor {
    private final static Logger log = Logger.getLogger(DataPacketProcessor.class);

    public Packet<SdwnPacketType> processDataPacket(Packet<SdwnPacketType> packet){


        return packet;
    }
}
