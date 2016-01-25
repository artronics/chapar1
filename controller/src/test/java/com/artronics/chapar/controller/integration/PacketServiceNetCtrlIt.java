package com.artronics.chapar.controller.integration;

import com.artronics.chapar.controller.factories.PacketFactory;
import com.artronics.chapar.controller.helpers.FakeBufferFactory;
import com.artronics.chapar.controller.sdwn.SdwnPacketFactory;
import com.artronics.chapar.controller.sdwn.controller.SdwnNetworkController;
import com.artronics.chapar.controller.sdwn.packet.BaseSdwnPacket;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.controller.services.NetworkController;
import com.artronics.chapar.controller.services.impl.NodeRegistrationServiceImpl;
import com.artronics.chapar.controller.services.impl.PacketServiceImpl;
import com.artronics.chapar.controller.services.impl.UnicastAddressRegistrationService;
import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import com.artronics.chapar.core.map.NodeMap;
import com.artronics.chapar.core.map.NodeMapImpl;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PacketServiceNetCtrlIt {
    private final static Logger log = Logger.getLogger(PacketServiceNetCtrlIt.class);

    private Device device;

    private Node n30;
    private Node n39;

    private PacketServiceImpl packetService;
    private NodeRegistrationServiceImpl nodeRegistrationService;
    private UnicastAddressRegistrationService addressRegistrationService;
    private PacketFactory packetFactory;
    private NetworkController<SdwnPacketType,BaseSdwnPacket> networkController;

    private Map<Long,Device> registeredDevices;
    private Map<Node,Node> registeredNodes;
    private Set<Address> unicastAddressSpace;
    private NodeMap nodeMap;

    @Before
    public void setUp() throws Exception {
        device = new Device(1L);
        registeredDevices = new HashMap<>();
        registeredDevices.put(device.getId(),device);

        registeredNodes = new HashMap<>();
        unicastAddressSpace = new HashSet<>();
        nodeMap = new NodeMapImpl();

        packetService = new PacketServiceImpl();
        packetFactory = new SdwnPacketFactory();
        nodeRegistrationService = new NodeRegistrationServiceImpl();
        addressRegistrationService = new UnicastAddressRegistrationService();
        networkController = new SdwnNetworkController();

        addressRegistrationService.setUnicastAddressSpace(unicastAddressSpace);

        nodeRegistrationService.setNodeMap(nodeMap);
        nodeRegistrationService.setRegisteredNodes(registeredNodes);

        packetService.setRegisteredDevices(registeredDevices);
        packetService.setPacketFactory(packetFactory);
        packetService.setNodeRegistrationService(nodeRegistrationService);
        packetService.setAddressRegistrationService(addressRegistrationService);
        packetService.setNetworkController(networkController);

        n30 = Node.create(Address.create(device,30L));
        n39 = Node.create(Address.create(device,39L));
    }
    
    @Test
    public void it_should_updateMap() throws MalformedPacketException {
        Packet packet = new Packet(FakeBufferFactory.createReportBuffer().getContent());
        packetService.addPacket(packet,device.getId());
        assertThat(nodeMap.hasLink(n30,n39),is(true));
    }

}
