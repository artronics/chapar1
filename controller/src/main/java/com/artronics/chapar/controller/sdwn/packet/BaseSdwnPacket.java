package com.artronics.chapar.controller.sdwn.packet;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Packet;
import org.apache.log4j.Logger;

import java.util.List;

public class BaseSdwnPacket extends Packet<SdwnPacketType> {
    private final static Logger log = Logger.getLogger(BaseSdwnPacket.class);

    public BaseSdwnPacket(List<Integer> content) {
        super(content);

        this.type = PacketUtils.getType(content);

        Long srcShortAdd =Integer.toUnsignedLong(PacketUtils.getSourceAddress(content));
        Long dstShortAdd =Integer.toUnsignedLong(PacketUtils.getDestinationAddress(content));

        this.srcAddress = Address.create(srcShortAdd);
        this.dstAddress = Address.create(dstShortAdd);

        this.direction = Direction.RX;
    }
}
