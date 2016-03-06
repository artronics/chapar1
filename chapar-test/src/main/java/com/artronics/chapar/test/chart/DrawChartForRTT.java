package com.artronics.chapar.test.chart;

import com.artronics.chapar.controller.repositories.PacketRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DrawChartForRTT {
    private final static Logger log = Logger.getLogger(DrawChartForRTT.class);

    private long clientId;
    private PacketRepo packetRepo;


    @Autowired
    public void setPacketRepo(PacketRepo packetRepo) {
        this.packetRepo = packetRepo;
    }

    @Value("${com.artronics.chapar.test.client_id}")
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

}
