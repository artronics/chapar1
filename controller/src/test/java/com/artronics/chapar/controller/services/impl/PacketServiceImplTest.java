package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.BufferRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class PacketServiceImplTest {

    @InjectMocks
    private PacketServiceImpl packetService;
    @Mock
    private BufferRepo bufferRepo;

    private Map<Client,Client> registeredClients;
    private BlockingQueue<Packet> packetQueue;

    private Client client;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        client = new Client();
        client.setId(1L);
        registeredClients = new HashMap<>();
        packetQueue = new LinkedBlockingQueue<>();

        packetService.setRegisteredClients(registeredClients);
        packetService.setPacketQueue(packetQueue);
    }

    @Test
    public void it_should_call_bufferRepo_for_every_registeredClient() throws Exception {
        client.setId(1L);
        Client c2 = new Client();
        c2.setId(2L);
        registeredClients.put(client,client);
        registeredClients.put(c2,c2);

        Buffer b1 = new Buffer(null, Buffer.Direction.RX,client);
        Buffer b2 = new Buffer(null, Buffer.Direction.RX,client);
        List<Buffer> buffs1 = new ArrayList<>(Arrays.asList(b1,b2));

        Buffer b3 = new Buffer(null, Buffer.Direction.RX,c2);
        Buffer b4 = new Buffer(null, Buffer.Direction.RX,c2);
        List<Buffer> buffs2 = new ArrayList<>(Arrays.asList(b3,b4));

        when(bufferRepo.getNewClientsBuffer(client)).thenReturn(buffs1);
        when(bufferRepo.getNewClientsBuffer(c2)).thenReturn(buffs2);

        packetService.checkForNewBuffersFromClients();

        assertThat(packetQueue.size(),is(equalTo(4)));
    }
}