package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class RuleRequestPacketProcessor {
    private final static Logger log = Logger.getLogger(RuleRequestPacketProcessor.class);

    Packet<SdwnPacketType> processRuleRequestPacket(Packet<SdwnPacketType> packet){

        return packet;
    }
}
