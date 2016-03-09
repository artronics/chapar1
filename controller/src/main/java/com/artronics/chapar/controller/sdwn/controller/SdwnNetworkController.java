package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.controller.AbstractNetworkController;
import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.log.PacketLogger;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SdwnNetworkController extends AbstractNetworkController<SdwnPacketType> {
    private final static Logger log = Logger.getLogger(SdwnNetworkController.class);
    private PacketLogger<SdwnPacketType> packetLogger;

    private ReportPacketProcessor reportPacketProcessor;
    private RuleRequestPacketProcessor ruleRequestPacketProcessor;

    @Override
    public Packet<SdwnPacketType> processPacket(Packet<SdwnPacketType> packet) {
        super.processPacket(packet);
        switch (packet.getType()) {
            case REPORT:
                processReportPacket(packet);
                return null;
            case DATA:
                processDataPacket(packet);
                return packet;
            case RL_REQ:
                processRuleRequestPacket(packet);
                return packet;
            case OPN_PT:
                processOpenPathPacket(packet);
                return null;
            case RL_RES:
                processRuleResponsePacket(packet);
                return null;
        }

        return null;
    }

    private Packet<SdwnPacketType> processReportPacket(Packet<SdwnPacketType> packet) {
        packetLogger.log(packet);
        return reportPacketProcessor.processReportPacket(packet);
    }

    private Packet<SdwnPacketType> processDataPacket(Packet<SdwnPacketType> packet) {
        packetLogger.log(packet);
        //All the process of registration and persistence is
        //already done in base class

        //if dir is tx it means we need to pass this packet to controller to
        //forward to client
        if (packet.getBuffer().getDirection() == Buffer.Direction.TX)
            return packet;

        return null;
    }

    private Packet<SdwnPacketType> processRuleRequestPacket(Packet<SdwnPacketType> packet) {
        packetLogger.log(packet);
        ruleRequestPacketProcessor.processRuleRequestPacket(packet);

        return packet;
    }

    private Packet<SdwnPacketType> processOpenPathPacket(Packet<SdwnPacketType> packet) {
        packetLogger.log(packet);

        return packet;
    }

    private Packet<SdwnPacketType> processRuleResponsePacket(Packet<SdwnPacketType> packet) {
        packetLogger.log(packet);

        return packet;
    }

    @Autowired
    public void setReportPacketProcessor(ReportPacketProcessor reportPacketProcessor) {
        this.reportPacketProcessor = reportPacketProcessor;
    }

    @Autowired
    public void setRuleRequestPacketProcessor(RuleRequestPacketProcessor ruleRequestPacketProcessor) {
        this.ruleRequestPacketProcessor = ruleRequestPacketProcessor;
    }

    @Autowired
    public void setPacketLogger(PacketLogger<SdwnPacketType> packetLogger) {
        this.packetLogger = packetLogger;
    }

}
