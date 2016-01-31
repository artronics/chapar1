package com.artronics.chapar.controller.sdwn.packet;

import com.artronics.chapar.controller.entities.packet.PacketFactory;
import com.artronics.chapar.controller.entities.packet.SdwnPacket;
import com.artronics.chapar.controller.exceptions.MalformedPacketException;
import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SdwnPacketFactory implements PacketFactory{
    private final static Logger log = Logger.getLogger(SdwnPacketFactory.class);

    @Override
    public SdwnPacket create(Buffer buffer) throws MalformedPacketException {
        return new SdwnPacket();
    }
}
