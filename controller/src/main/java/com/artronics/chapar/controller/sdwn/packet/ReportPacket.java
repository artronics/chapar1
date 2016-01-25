package com.artronics.chapar.controller.sdwn.packet;

import java.util.List;

public class ReportPacket extends BaseSdwnPacket{

    private ReportPacket(List<Integer> content) {
        super(content);
        this.type = SdwnPacketType.REPORT;
    }

    public static ReportPacket create(List<Integer> content) {
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
