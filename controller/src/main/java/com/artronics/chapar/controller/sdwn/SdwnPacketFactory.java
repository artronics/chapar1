package com.artronics.chapar.controller.sdwn;

import com.artronics.chapar.controller.factories.PacketFactory;
import com.artronics.chapar.controller.sdwn.packet.PacketUtils;
import com.artronics.chapar.controller.sdwn.packet.ReportPacket;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SdwnPacketFactory implements PacketFactory {
    private final static Logger log = Logger.getLogger(SdwnPacketFactory.class);

    @Override
    public Packet create(Packet packet) throws MalformedPacketException {
        List<Integer> content = packet.getContent();
        SdwnPacketType type = PacketUtils.getType(content);
        switch (type){
            case REPORT:
                return ReportPacket.create(content);
        }

        throw new MalformedPacketException();
    }
}
