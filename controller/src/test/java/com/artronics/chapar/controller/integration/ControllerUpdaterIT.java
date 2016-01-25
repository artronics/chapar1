package com.artronics.chapar.controller.integration;

import com.artronics.chapar.controller.sdwn.helpers.FakeSdwnBufferFactory;
import com.artronics.chapar.controller.services.PacketService;
import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;
import com.artronics.chapar.core.map.NodeMap;
import com.artronics.chapar.core.support.NodeMapPrinter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ControllerUpdaterIT.CtrlUpConfig.class})
@TestPropertySource("classpath:controller-test-config.properties")
public class ControllerUpdaterIT {
    private static final NodeMapPrinter printer = new NodeMapPrinter();
    private Device device;

    private Node sink;
    private Node n30;
    private Node n39;
    private Node n40;

    @Autowired
    private PacketService packetService;


    @Resource(name = "nodeMap")
    private NodeMap nodeMap;

    @Resource(name = "registeredDevices")
    private Map<Long, Device> registeredDevices;

    @Before
    public void setUp() throws Exception {
        device = new Device(1L);

        sink = Node.create(Address.create(device, 0L));
        n30 = Node.create(Address.create(device, 30L));
        n39 = Node.create(Address.create(device, 39L));
        n40 = Node.create(Address.create(device, 40L));

        registeredDevices.put(device.getId(), device);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void it_should_create_links_based_on_ReportPacket() throws Exception {
        firstPacket();

        assertThat(nodeMap.hasLink(n39, n30), is(true));
        assertThat(nodeMap.hasLink(n39, n40), is(true));

        assertThat(nodeMap.hasLink(n30, n40), is(false));
        assertThat(nodeMap.hasLink(n30, sink), is(false));
        assertThat(nodeMap.hasLink(n39,sink),is(false));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void it_should_update_map_when_receive_new_reports() throws Exception {
        firstPacket();
        secondPacket();

        assertThat(nodeMap.hasLink(n30,n40),is(true));
        assertThat(nodeMap.hasLink(n30,sink),is(true));

        assertThat(nodeMap.hasLink(n40,sink),is(false));
        assertThat(nodeMap.hasLink(n39,sink),is(false));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void it_should_keep_the_state_of_previous_packet() throws Exception {
        firstPacket();

        secondPacket();

        assertThat(nodeMap.hasLink(n30,sink),is(true));
        assertThat(nodeMap.hasLink(n30,n40),is(true));
        assertThat(nodeMap.hasLink(n39, n40), is(true));

        assertThat(nodeMap.hasLink(n39, n30), is(false));
        assertThat(nodeMap.hasLink(n40,sink),is(false));
    }

    @Test
    public void it_should_keep_the_previous_status_of_neighbor() throws Exception {
        firstPacket();
        secondPacket();

        //during second packet node39 should have status of ACTIVE
        //because it sent a packet as source
        assertThat(n39.getStatus(),is(equalTo(Node.Status.ACTIVE)));
    }

    private void secondPacket() throws MalformedPacketException, NodeNotRegistered {
        Packet packet2 = createReportPacket(n30, sink, n40, sink);
        packetService.addPacket(packet2, device.getId());
        System.out.println(printer.printDeviceMap(nodeMap, device));
    }

    private void firstPacket() throws MalformedPacketException, NodeNotRegistered {
        Packet packet = createReportPacket(n39, sink, n30, n40);
        packetService.addPacket(packet, device.getId());
        System.out.println(printer.printDeviceMap(nodeMap, device));
    }

    private static Packet createReportPacket(Node src, Node dst, Node... neighbors) {
        Integer[] ne = new Integer[neighbors.length];
        for (int i = 0; i < ne.length; i++) {
            ne[i] = neighbors[i].getAddress().getLocalAdd().intValue();
        }

        Packet packet = new Packet(FakeSdwnBufferFactory.createReportBuffer(
                src.getAddress().getLocalAdd().intValue(),
                dst.getAddress().getLocalAdd().intValue(),
                ne
        ).getContent());

        return packet;
    }

    @ComponentScan({"com.artronics.chapar.controller",
            "com.artronics.chapar.core"})
    static class CtrlUpConfig {

    }
}
