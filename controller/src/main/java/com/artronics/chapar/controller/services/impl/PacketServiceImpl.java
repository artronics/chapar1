package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.services.PacketService;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.BufferRepo;
import com.artronics.chapar.domain.repositories.TimeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

@Service
public class PacketServiceImpl implements PacketService{
    private final static Logger log = Logger.getLogger(PacketServiceImpl.class);

    private Map<Client,Client> registeredClients;
    private BlockingQueue<Packet> packetQueue;

    private BufferRepo bufferRepo;
    private TimeRepo timeRepo;

    @Override
    public void checkRxBuffers() {
        checkForNewBuffersFromClients();
    }

    @Scheduled(fixedRateString = "${com.artronics.chapar.controller.scheduler.rate}")
    public void checkForNewBuffersFromClients(){
        Set<Client> clients = registeredClients.keySet();
        clients.forEach(client -> {

            List<Buffer> buffers = bufferRepo.getNewClientsBuffer(client);
            if (!buffers.isEmpty()) {
                log.debug("Received "+buffers.size()+ " new buffer from Client id:"+client.getId());
                buffers.forEach(b -> {
                    b.setProcessedAt(timeRepo.getDbNowTime());
                    bufferRepo.save(b);

                    Packet packet = Packet.create(b);
                    packetQueue.add(packet);

                });
            }
        });
    }

    @Resource(name = "registeredClients")
    public void setRegisteredClients(Map<Client, Client> registeredClients) {
        this.registeredClients = registeredClients;
    }

    @Resource(name = "packetQueue")
    public void setPacketQueue(BlockingQueue<Packet> packetQueue) {
        this.packetQueue = packetQueue;
    }

    @Autowired
    public void setBufferRepo(BufferRepo bufferRepo) {
        this.bufferRepo = bufferRepo;
    }

    @Autowired
    public void setTimeRepo(TimeRepo timeRepo) {
        this.timeRepo = timeRepo;
    }
}
