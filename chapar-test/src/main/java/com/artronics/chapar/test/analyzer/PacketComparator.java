package com.artronics.chapar.test.analyzer;

import com.artronics.chapar.controller.entities.packet.SdwnPacket;
import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;

import java.util.Comparator;

public class PacketComparator implements Comparator<SdwnPacket>{
    private final static Logger log = Logger.getLogger(PacketComparator.class);

    @Override
    public int compare(SdwnPacket o1, SdwnPacket o2) {
        Buffer b1 = o1.getBuffer();
        Buffer b2 = o2.getBuffer();

        if (o1.equals(o2))
            return 0;

        if (b1.getSentAt()!=null && b2.getSentAt()!=null){
            if (b1.getSentAt().after(b2.getSentAt()))
                return 1;
            else
                return -1;
        }

        if (b1.getSentAt()==null && b2.getSentAt()==null){
            if (b1.getReceivedAt().after(b2.getReceivedAt()))
                return 1;
            else
                return -1;
        }

        if (b1.getSentAt()!=null) {
            if (b1.getSentAt().before(b2.getReceivedAt())) {
                return -1;
            }
            else
                return 1;
        }

        if (b2.getSentAt()!=null){
            if (b2.getSentAt().after(b1.getReceivedAt()))
                return 1;
            else
                return -1;
        }

        return 0;
    }
}
