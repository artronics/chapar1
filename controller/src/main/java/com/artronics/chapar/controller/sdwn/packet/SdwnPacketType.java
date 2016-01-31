package com.artronics.chapar.controller.sdwn.packet;


import com.artronics.chapar.controller.entities.packet.PacketType;

public enum SdwnPacketType implements PacketType {
    DATA(0),
    MALFORMED(1),
    REPORT(2),
    BEACON(3),
    RL_REQ(128),
    RL_RES(4),
    OPN_PT(5);

    private int value;

    SdwnPacketType(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}
