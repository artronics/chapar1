package com.artronics.chapar.controller.sdwn.packet;

import com.artronics.chapar.core.entities.Packet;

import java.util.List;

public class ReportPacket extends Packet<SdwnPacketType>{

    public ReportPacket(List<Integer> content) {
        super(content);
        this.type = SdwnPacketType.REPORT;
    }

    public static Packet create(List<Integer> content) {
        return new ReportPacket(content);
    }

    @Override
    public SdwnPacketType getType() {
        return SdwnPacketType.REPORT;
    }

    @Override
    public String toString()
    {
        return "ReportPacket->"+super.toString();
    }

}
