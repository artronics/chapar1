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

    private static final String RL_REQ= BASE+".ruleRequest";
    private static final Logger RR_LOG = Logger.getLogger(RL_REQ);

    private static final String RL_RES= BASE+".ruleResponse";
    private static final Logger RRES_LOG = Logger.getLogger(RL_RES);

    private static final String OPN_PT= BASE+".openPath";
    private static final Logger OP_LOG = Logger.getLogger(OPN_PT);

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
            case RL_REQ:
                RR_LOG.debug("rule request packet received");
                break;
            case RL_RES:
                RRES_LOG.debug("rule response packet received");
                break;
            case OPN_PT:
                OP_LOG.debug("open path packet received");
                break;
        }
    }
}
