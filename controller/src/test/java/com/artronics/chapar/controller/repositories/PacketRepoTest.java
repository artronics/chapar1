package com.artronics.chapar.controller.repositories;

import com.artronics.chapar.controller.entities.packet.SdwnPacket;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.domain.repositories.PersistenceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={PersistenceConfig.class, PacketRepoTest.RepoConfig.class})
@TestPropertySource("classpath:controller-test-config.properties")
public class PacketRepoTest {
    @Autowired
    private PacketRepo packetRepo;

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void it_should_save_a_packet() throws Exception {
        SdwnPacket p = new SdwnPacket();
        p.setType(SdwnPacketType.REPORT);
        packetRepo.save(p);

        assertThat(p.getId(),is(notNullValue()));
    }

    @Configuration
    @PropertySource("classpath:h2-config.properties")
    @ComponentScan("com.artronics.chapar.controller")
    static class RepoConfig{

    }
}