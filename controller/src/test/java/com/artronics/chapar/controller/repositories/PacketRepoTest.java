package com.artronics.chapar.controller.repositories;

import com.artronics.chapar.controller.BaseControllerTestConfig;
import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.entities.packet.SdwnPacket;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.address.Address;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.repositories.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={PersistenceConfig.class, PacketRepoTest.RepoConfig.class})
@TestPropertySource("classpath:controller-test-config.properties")
public class PacketRepoTest {
    @Autowired
    private PacketRepo packetRepo;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private ControllerRepo controllerRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private BufferRepo bufferRepo;

    private Client client;
    private UnicastAddress srcAdd;
    private Address dstAdd;
    private Buffer buffer;

    @Before
    public void setUp() throws Exception {
        Controller ctrl = new Controller();
        controllerRepo.save(ctrl);
        client = new Client(ctrl);
        clientRepo.save(client);

        srcAdd = UnicastAddress.create(client,1000L);
        dstAdd = UnicastAddress.create(client,2000L);

        addressRepo.save(srcAdd);
        addressRepo.save(dstAdd);

        buffer = new Buffer(Arrays.asList(1,2,3,4,5,6,2,5,4,3,2,3,45,6), Buffer.Direction.TX);
        bufferRepo.save(buffer);
    }

    @Test
    public void it_should_save_a_packet() throws Exception {
        SdwnPacket p = new SdwnPacket();
        p.setType(SdwnPacketType.REPORT);
        packetRepo.save(p);

        assertThat(p.getId(),is(notNullValue()));
    }

    @Test
    public void it_should_save_packet_with_addresses() throws Exception {
        Packet p = new SdwnPacket();
        p.setType(SdwnPacketType.REPORT);
        p.setBuffer(buffer);
        p.setSrcAddress(srcAdd);
        p.setDstAddress(dstAdd);

        packetRepo.save(p);

        assertThat(p.getId(),is(notNullValue()));
    }

    @Configuration
    @PropertySource("classpath:h2-config.properties")
    @ComponentScan("com.artronics.chapar.controller.repositories")
    @Import(BaseControllerTestConfig.class)
    static class RepoConfig{

    }
}