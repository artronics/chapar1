package com.artronics.chapar.controller.entities.packet;

import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "packets")
public class SdwnPacket extends Packet<SdwnPacketType>{
    private final static Logger log = Logger.getLogger(SdwnPacket.class);
}
