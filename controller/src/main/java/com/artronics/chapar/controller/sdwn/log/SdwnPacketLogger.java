package com.artronics.chapar.controller.sdwn.log;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.log.BasePacketLogger;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SdwnPacketLogger extends BasePacketLogger<SdwnPacketType> {
    private static final String REPORT= BASE+".report";
    private static final Logger REP_LOG = Logger.getLogger(REPORT);

    private static final String DATA= BASE+".data";
    private static final Logger DATA_LOG = Logger.getLogger(DATA);

    @Override
    public void log(Packet<SdwnPacketType> packet) {
        super.log(packet);
        switch (packet.getType()){
            case REPORT:
                REP_LOG.debug("report packet received");
                break;
            case DATA:
                DATA_LOG.debug("data packet received");
                break;
        }
    }
}
