package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.sdwn.packet.BaseSdwnPacket;
import com.artronics.chapar.controller.sdwn.packet.ReportPacket;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.controller.services.NetworkController;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("sdwnNetworkController")
public class SdwnNetworkController implements NetworkController<SdwnPacketType,BaseSdwnPacket>{
    private final static Logger log = Logger.getLogger(SdwnNetworkController.class);

    @PostConstruct
    public void initBean(){
        log.debug("Initializing SdwnController as NetworkController");
    }

    @Override
    public Packet processPacket(BaseSdwnPacket packet) throws MalformedPacketException {
        switch (packet.getType()){
            case REPORT:
                processReportPacket((ReportPacket) packet);
                return null;

            case MALFORMED:
                throw new MalformedPacketException();
        }

        throw new MalformedPacketException();
    }

    private void processReportPacket(ReportPacket packet){

    }
}
