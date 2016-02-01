package com.artronics.chapar.controller.integration;

import com.artronics.chapar.controller.BaseControllerTestConfig;
import com.artronics.chapar.controller.config.ControllerResourceConfig;
import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.entities.packet.PacketFactory;
import com.artronics.chapar.controller.sdwn.helpers.FakeSdwnBufferFactory;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketFactory;
import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.controller.services.PacketRegistrationService;
import com.artronics.chapar.controller.services.PacketService;
import com.artronics.chapar.controller.services.impl.AddressRegistrationServiceImpl;
import com.artronics.chapar.controller.services.impl.PacketRegistrationServiceImpl;
import com.artronics.chapar.controller.services.impl.PacketServiceImpl;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.address.Address;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.repositories.*;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.BlockingQueue;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PacketServiceIT.ConfigIT.class})
@TestPropertySource({"classpath:controller-test-config.properties"})
public class PacketServiceIT {
    private final static Logger log = Logger.getLogger(PacketServiceIT.class);
    private FakeSdwnBufferFactory fakeBufferFactory = new FakeSdwnBufferFactory();

    @Autowired
    private ControllerRepo controllerRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private BufferRepo bufferRepo;
    @Autowired
    private TimeRepo timeRepo;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private PacketService packetService;

    @Resource(name = "registeredClients")
    private Map<Client,Client> registeredClients;
    @Resource(name = "packetQueue")
    private BlockingQueue<Packet> packetQueue;
    @Resource(name = "unicastAddresses")
    private Map<UnicastAddress,UnicastAddress> unicastAddresses;

    private Client client;

    private Long srcLocalAdd = 1000L;
    private Long dstLocalAdd = 2000L;
    private UnicastAddress srcAdd;
    private Address dstAdd;
    @Before
    public void setUp() throws Exception {
        Controller ctrl = new Controller();
        controllerRepo.save(ctrl);
        client = new Client(ctrl);
        clientRepo.save(client);
        registeredClients.put(client,client);

        srcAdd = UnicastAddress.create(client,srcLocalAdd);
        dstAdd = UnicastAddress.create(client,dstLocalAdd);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void it_should_add_processedAt_timestamp_to_buffers() throws Exception {
        saveRxBuffers();
        packetService.checkForRxBuffers();

       List<Buffer> buffs = bufferRepo.getProcessedRxBuffs();

        assertThat(buffs.size(),is(equalTo(2)));
        buffs.forEach(b->assertThat(b.getProcessedAt(),is(notNullValue())));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void it_should_register_src_and_dst_address() throws Exception {
        //two times to make sure it does not register an address twice
        saveRxBuffers();
        saveRxBuffers();

        packetService.checkForRxBuffers();

        //2 because one for src and one for dst
        assertThat(unicastAddresses.size(),is(equalTo(2)));
    }

    @Test
    public void it_should_add_persisted_packet_into_queue() throws Exception {
        saveRxBuffers();

        packetService.checkForRxBuffers();

        Packet packet = packetQueue.take();

        assertThat(packet.getId(),is(notNullValue()));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void it_should_persist_src_and_dst_address() throws Exception {
        saveRxBuffers();
        saveRxBuffers();

        packetService.checkForRxBuffers();

        UnicastAddress actSrcAdd = unicastAddresses.get(srcAdd);
        assertThat(actSrcAdd.getId(),is(notNullValue()));

        Address actDstAdd = unicastAddresses.get(dstAdd);
        assertThat(actDstAdd.getId(),is(notNullValue()));
    }

    private void saveRxBuffers(){
        Buffer b1 = new Buffer(createBuffContent(), Buffer.Direction.RX,client);
        Buffer b2 = new Buffer(createBuffContent(), Buffer.Direction.RX,client);
        List<Buffer> buffs1 = new ArrayList<>(Arrays.asList(b1,b2));
        b1.setSentAt(timeRepo.getDbNowTime());
        b2.setSentAt(timeRepo.getDbNowTime());

        bufferRepo.save(buffs1);
    }

    private List<Integer> createBuffContent(){
        Buffer b = FakeSdwnBufferFactory.createReportBuffer
                (srcLocalAdd.intValue(),dstLocalAdd.intValue(),10,20);

        return b.getContent();
    }

    @Configuration
    @ComponentScan(basePackages = {"com.artronics.chapar.domain"})
    @Import({PersistenceConfig.class, BaseControllerTestConfig.class, ControllerResourceConfig.class})
    @PropertySource("classpath:mysql-config.properties")
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

        @Bean
        public PacketFactory getFactory(){
            return new SdwnPacketFactory();
        }

        @Bean
        public PacketRegistrationService getPacketRegService(){
            return new PacketRegistrationServiceImpl();
        }

        @Bean
        public AddressRegistrationService getAddReg(){
            return new AddressRegistrationServiceImpl();
        }
    }
}
