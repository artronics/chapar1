package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import org.apache.log4j.Logger;

public class ReportPacketProcessor {
    private final static Logger log = Logger.getLogger(ReportPacketProcessor.class);

    Packet<SdwnPacketType> processReportPacket(Packet<SdwnPacketType> packet){
        assert packet.getType()==SdwnPacketType.REPORT;

        return packet;
    }
}
