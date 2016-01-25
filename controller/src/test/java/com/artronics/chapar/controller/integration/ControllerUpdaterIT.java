package com.artronics.chapar.controller.integration;

import com.artronics.chapar.controller.sdwn.helpers.FakeSdwnBufferFactory;
import com.artronics.chapar.controller.services.PacketService;
import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.map.NodeMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ControllerUpdaterIT.CtrlUpConfig.class})
@TestPropertySource("classpath:controller-test-config.properties")
public class ControllerUpdaterIT {
    private Device device;

    private Node n30;
    private Node n39;

    @Autowired
    private PacketService packetService;


    @Resource(name = "nodeMap")
    private NodeMap nodeMap;

    @Resource(name = "registeredDevices")
    private Map<Long,Device> registeredDevices;

    @Before
    public void setUp() throws Exception {
        device = new Device(1L);

        n30 = Node.create(Address.create(device,30L));
        n39 = Node.create(Address.create(device,39L));

        registeredDevices.put(device.getId(),device);
    }

    @Test
    public void it_should_Name() throws Exception {
        Packet packet = new Packet(FakeSdwnBufferFactory.createReportBuffer().getContent());
        packetService.addPacket(packet,device.getId());
        assertThat(nodeMap.hasLink(n30,n39),is(true));

    }

    @ComponentScan({"com.artronics.chapar.controller",
    "com.artronics.chapar.core"})
    static class CtrlUpConfig{

    }
}
