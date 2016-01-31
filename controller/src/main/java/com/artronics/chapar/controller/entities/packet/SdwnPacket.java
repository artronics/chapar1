package com.artronics.chapar.controller.entities.packet;

import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "packets")
public class SdwnPacket extends Packet<SdwnPacketType>{
    private final static Logger log = Logger.getLogger(SdwnPacket.class);

    public SdwnPacket() {
    }

    public SdwnPacket(Buffer buffer) {
        super(buffer);
    }
}
