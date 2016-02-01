package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.controller.AbstractNetworkController;
import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SdwnNetworkController extends AbstractNetworkController<SdwnPacketType>{
    private final static Logger log = Logger.getLogger(SdwnNetworkController.class);
    private final ReportPacketProcessor reportPacketProcessor;

    public SdwnNetworkController() {
        reportPacketProcessor = new ReportPacketProcessor();
    }

    @Override
    public Packet<SdwnPacketType> processPacket(Packet<SdwnPacketType> packet) {
        super.processPacket(packet);
        switch (packet.getType()){
            case REPORT:
                processReportPacket(packet);
                return packet;

        }

        return null;
    }

    private Packet<SdwnPacketType> processReportPacket(Packet<SdwnPacketType> packet) {
        return reportPacketProcessor.processReportPacket(packet);
    }
}
