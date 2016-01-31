package com.artronics.chapar.controller.sdwn.packet;

import com.artronics.chapar.controller.entities.packet.PacketFactory;
import com.artronics.chapar.controller.entities.packet.SdwnPacket;
import com.artronics.chapar.controller.exceptions.MalformedPacketException;
import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SdwnPacketFactory implements PacketFactory{
    private final static Logger log = Logger.getLogger(SdwnPacketFactory.class);

    @Override
    public SdwnPacket create(Buffer buffer) throws MalformedPacketException {
        List<Integer> content = buffer.getContent();

        SdwnPacket packet = new SdwnPacket(buffer);

        SdwnPacketType type = SdwnPacketUtils.getType(content);
        packet.setType(type);

        Long srcAdd = Integer.toUnsignedLong(SdwnPacketUtils.getSourceAddress(content));
        packet.setSrcLocalAddress(srcAdd);

        Long dstAdd = Integer.toUnsignedLong(SdwnPacketUtils.getDestinationAddress(content));
        packet.setDstLocalAddress(dstAdd);

        return packet;
    }
}
