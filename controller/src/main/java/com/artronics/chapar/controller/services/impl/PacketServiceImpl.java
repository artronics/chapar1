package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.controller.NetworkController;
import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.entities.packet.PacketFactory;
import com.artronics.chapar.controller.exceptions.MalformedPacketException;
import com.artronics.chapar.controller.services.PacketRegistrationService;
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

    private NetworkController networkController;

    private PacketFactory packetFactory;
    private PacketRegistrationService packetRegistrationService;

    private BufferRepo bufferRepo;
    private TimeRepo timeRepo;

    @Override
    public void checkForRxBuffers() {
        checkForNewBuffersFromClients();
    }

    @Scheduled(fixedRateString = "${com.artronics.chapar.controller.scheduler.rate}")
    public void checkForNewBuffersFromClients(){
        Set<Client> clients = registeredClients.keySet();
        clients.forEach(client -> {

            List<Buffer> buffers = bufferRepo.getNewClientsBuffer(client);
            if (!buffers.isEmpty()) {
                log.debug("Received "+buffers.size()+ " new buffer from Client id:"+client.getId());

                buffers.forEach(b-> createAndRegisterPacketAndAddToQueue(b,client));
            }
        });
    }

    @Override
    public void receiveBuffer(Buffer buffer) {
        //If the direction of buffer is tx, it means we just need to
        //redirect it to client to send to sink device
        if (buffer.getDirection()== Buffer.Direction.TX) {
            Client regClient = registeredClients.get(buffer.getClient());
            buffer.setClient(regClient);
            bufferRepo.save(buffer);

            createAndRegisterPacketAndAddToQueue(buffer, buffer.getClient());
        }
    }

    @Override
    public Buffer receiveBufferAndGetResponse(Buffer buffer) {
        Client regClient = registeredClients.get(buffer.getClient());
        buffer.setClient(regClient);
        bufferRepo.save(buffer);

        return createAndRegisterPacketAndSendToController(buffer,regClient);
    }

    private void createAndRegisterPacketAndAddToQueue(Buffer b, Client client) {
        Packet packet = createAndRegisterPacket(b, client);

        packetQueue.add(packet);
    }

    private Buffer createAndRegisterPacketAndSendToController(Buffer b, Client client) {
        Packet packet = createAndRegisterPacket(b, client);

        Packet resPacket =networkController.processPacket(packet);

        return resPacket.getBuffer();
    }


    private Packet createAndRegisterPacket(Buffer b, Client client) {
        b.setProcessedAt(timeRepo.getDbNowTime());
        bufferRepo.save(b);

        Packet packet = null;
        try {
            packet = packetFactory.create(b);

        } catch (MalformedPacketException e) {
            //TODO Persist a malformed packet in db
            e.printStackTrace();
        }

        packet.setGeneratedAt(timeRepo.getDbNowTime());

        packetRegistrationService.registerPacket(packet,client);
        return packet;
    }

    @Override
    public Packet receivePacket(Packet packet) throws MalformedPacketException {
        validatePacket(packet);


        return packet;
    }

    private void validatePacket(Packet packet) throws MalformedPacketException {
        if (
                packet.getSrcAddress()==null ||
                packet.getSrcAddress().getClient()==null ||
                !registeredClients.containsKey(packet.getSrcAddress().getClient())){

            throw new MalformedPacketException();
        }
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
    public void setNetworkController(NetworkController networkController) {
        this.networkController = networkController;
    }

    @Autowired
    public void setPacketFactory(PacketFactory packetFactory) {
        this.packetFactory = packetFactory;
    }

    @Autowired
    public void setPacketRegistrationService(PacketRegistrationService packetRegistrationService) {
        this.packetRegistrationService = packetRegistrationService;
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
