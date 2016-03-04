package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.controller.AbstractNetworkController;
import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.log.PacketLogger;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SdwnNetworkController extends AbstractNetworkController<SdwnPacketType>{
    private final static Logger log = Logger.getLogger(SdwnNetworkController.class);
    private PacketLogger<SdwnPacketType> packetLogger;

    private ReportPacketProcessor reportPacketProcessor;

    @Override
    public Packet<SdwnPacketType> processPacket(Packet<SdwnPacketType> packet) {
        super.processPacket(packet);
        switch (packet.getType()){
            case REPORT:
                processReportPacket(packet);
                return packet;
            case DATA:
                processDataPacket(packet);
                return packet;
            case RL_REQ:
                processRuleRequestPacket(packet);
                return packet;

        }

        return null;
    }

    private Packet<SdwnPacketType> processReportPacket(Packet<SdwnPacketType> packet) {
        packetLogger.log(packet);
        return reportPacketProcessor.processReportPacket(packet);
    }

    private Packet<SdwnPacketType> processDataPacket(Packet<SdwnPacketType> packet){
        packetLogger.log(packet);
        //Do nothing. all the process of registration and persistence is
        //already done in base class
        return packet;
    }

    private Packet<SdwnPacketType> processRuleRequestPacket(Packet<SdwnPacketType> packet){
        packetLogger.log(packet);

        return packet;
    }

    @Autowired
    public void setReportPacketProcessor(ReportPacketProcessor reportPacketProcessor) {
        this.reportPacketProcessor = reportPacketProcessor;
    }

    @Autowired
    public void setPacketLogger(PacketLogger<SdwnPacketType> packetLogger) {
        this.packetLogger = packetLogger;
    }

}
