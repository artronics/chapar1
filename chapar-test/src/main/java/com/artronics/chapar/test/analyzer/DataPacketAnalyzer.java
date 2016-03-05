package com.artronics.chapar.test.analyzer;

import com.artronics.chapar.controller.entities.packet.SdwnPacket;
import com.artronics.chapar.controller.repositories.PacketRepo;
import com.artronics.chapar.domain.entities.Client;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataPacketAnalyzer implements Analyzer{
    private final static Logger log = Logger.getLogger(DataPacketAnalyzer.class);

    private PacketRepo packetRepo;

    private long clientId;
    private Client client;

    @Override
    public void start() {
        client = new Client(clientId);
        List<SdwnPacket> packets = packetRepo.getDataPackets(client);
        System.out.println(packets.size());
    }

    @Autowired
    public void setPacketRepo(PacketRepo packetRepo) {
        this.packetRepo = packetRepo;
    }

    @Value("${com.artronics.chapar.test.client_id}")
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
}
