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
    private DataPacketProcessor dataPacketProcessor;

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

        }

        return null;
    }

    private Packet<SdwnPacketType> processReportPacket(Packet<SdwnPacketType> packet) {
        packetLogger.log(packet);
        return reportPacketProcessor.processReportPacket(packet);
    }

    private Packet<SdwnPacketType> processDataPacket(Packet<SdwnPacketType> packet){
        packetLogger.log(packet);

        return dataPacketProcessor.processDataPacket(packet);
    }

    @Autowired
    public void setReportPacketProcessor(ReportPacketProcessor reportPacketProcessor) {
        this.reportPacketProcessor = reportPacketProcessor;
    }

    @Autowired
    public void setDataPacketProcessor(DataPacketProcessor dataPacketProcessor) {
        this.dataPacketProcessor = dataPacketProcessor;
    }

    @Autowired
    public void setPacketLogger(PacketLogger<SdwnPacketType> packetLogger) {
        this.packetLogger = packetLogger;
    }

}
