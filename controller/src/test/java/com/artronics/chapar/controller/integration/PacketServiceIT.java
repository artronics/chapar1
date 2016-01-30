package com.artronics.chapar.controller.integration;

import com.artronics.chapar.controller.BaseControllerTestConfig;
import com.artronics.chapar.controller.config.ControllerResourceConfig;
import com.artronics.chapar.controller.services.PacketService;
import com.artronics.chapar.controller.services.impl.PacketServiceImpl;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.BufferRepo;
import com.artronics.chapar.domain.repositories.ClientRepo;
import com.artronics.chapar.domain.repositories.PersistenceConfig;
import com.artronics.chapar.domain.repositories.TimeRepo;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PacketServiceIT.ConfigIT.class})
@TestPropertySource({"classpath:application-dev.properties","classpath:controller-test-config.properties"})
public class PacketServiceIT {
    private final static Logger log = Logger.getLogger(PacketServiceIT.class);

    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private BufferRepo bufferRepo;
    @Autowired
    private TimeRepo timeRepo;
    @Autowired
    private PacketService packetService;
    @Resource(name = "registeredClients")
    private Map<Client,Client> registeredClients;

    private Client client;
    @Before
    public void setUp() throws Exception {

        client = new Client();
        clientRepo.save(client);
        registeredClients.put(client,client);
    }

    @Test
    public void it_should_Name() throws Exception {
        saveRxBuffers();
        packetService.checkRxBuffers();

    }

    private void saveRxBuffers(){
        Buffer b1 = new Buffer(null, Buffer.Direction.RX,client);
        Buffer b2 = new Buffer(null, Buffer.Direction.RX,client);
        List<Buffer> buffs1 = new ArrayList<>(Arrays.asList(b1,b2));
        b1.setSentAt(timeRepo.getDbNowTime());
        b2.setSentAt(timeRepo.getDbNowTime());

        bufferRepo.save(buffs1);
    }

    @Configuration
    @ComponentScan(basePackages = {"com.artronics.chapar.domain"})
    @Import({PersistenceConfig.class, BaseControllerTestConfig.class, ControllerResourceConfig.class})
    @PropertySource("classpath:application-dev.properties")
    static class ConfigIT{

        @Bean(name = "registeredClients")
        public Map<Client,Client> getRegisteredClients(){
            return new HashMap<>();
        }

        @Bean
        public PacketService getPacketService(){
            PacketServiceImpl packetService = new PacketServiceImpl();
            packetService.setRegisteredClients(getRegisteredClients());
            return packetService;
        }

    }
}
