package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.BufferRepo;
import com.artronics.chapar.domain.repositories.TimeRepo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class PacketServiceImplTest {

    @InjectMocks
    private PacketServiceImpl packetService;
    @Mock
    private BufferRepo bufferRepo;
    @Mock
    private TimeRepo timeRepo;

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

        registeredClients.put(client,client);
    }

    @Test
    public void it_should_call_bufferRepo_for_every_registeredClient() throws Exception {
        Client c2 = new Client();
        c2.setId(2L);
        registeredClients.put(c2,c2);

        addBuffers(c2);

        packetService.checkForNewBuffersFromClients();

        assertThat(packetQueue.size(),is(equalTo(4)));
    }

    private void addBuffers(Client c2) {
        Buffer b1 = new Buffer(null, Buffer.Direction.RX,client);
        Buffer b2 = new Buffer(null, Buffer.Direction.RX,client);
        List<Buffer> buffs1 = new ArrayList<>(Arrays.asList(b1,b2));
        when(bufferRepo.getNewClientsBuffer(client)).thenReturn(buffs1);

        if (c2 != null) {
            Buffer b3 = new Buffer(null, Buffer.Direction.RX,c2);
            Buffer b4 = new Buffer(null, Buffer.Direction.RX,c2);
            List<Buffer> buffs2 = new ArrayList<>(Arrays.asList(b3,b4));
            when(bufferRepo.getNewClientsBuffer(c2)).thenReturn(buffs2);
        }
    }

    @Test
    public void it_should_fill_packetQueue_with_generated_packets_out_of_buffers() throws Exception {
        addBuffers();
        packetService.checkForNewBuffersFromClients();

        assertThat(packetQueue.size(),is(equalTo(2)));
    }

    private void addBuffers() {
        addBuffers(null);
    }

    @Ignore("it doesn't work with mockito. Should test it with db")
    @Test
    public void it_should_update_processedAt_field_for_each_buffer() throws Exception {
        Client c2 = new Client();
        c2.setId(2L);
        registeredClients.put(c2,c2);

        addBuffers(c2);

        packetService.checkForNewBuffersFromClients();
        when(timeRepo.getDbNowTime()).thenReturn(new Timestamp(new Date().getTime()));

        ArgumentCaptor<Buffer> cap = ArgumentCaptor.forClass(Buffer.class);
        Mockito.verify(bufferRepo,times(4)).save(cap.capture());

        List<Buffer> allBuffs = cap.getAllValues();
        allBuffs.forEach(b-> assertThat(b.getProcessedAt(),is(notNullValue())));
    }

}