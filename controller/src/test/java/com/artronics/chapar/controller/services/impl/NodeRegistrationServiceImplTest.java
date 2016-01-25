package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.map.NodeMap;
import com.artronics.chapar.core.map.NodeMapImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.artronics.chapar.core.entities.Node.Status.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class NodeRegistrationServiceImplTest {

    @InjectMocks
    private NodeRegistrationServiceImpl nodeRegistrationService;

    @Mock
    private AddressRegistrationService addRegService;

    private Map<Node, Node> registeredNodes;
    private NodeMap nodeMap;

    private Device device;
    private Node srcNode;
    private Node dstNode;
    private Node aNode;
    private Node eqSrcNode;
    private Node eqDstNode;
    private Node eqANode;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        registeredNodes = new HashMap<>();
        nodeRegistrationService.setRegisteredNodes(registeredNodes);
        nodeMap = new NodeMapImpl();
        nodeRegistrationService.setNodeMap(nodeMap);

        device = new Device(1L);
        srcNode = Node.create(Address.create(device, 1L));
        dstNode = Node.create(Address.create(device, 2L));
        aNode = Node.create(Address.create(device,3L));

        eqSrcNode = Node.create(Address.create(device, 1L));
        eqDstNode = Node.create(Address.create(device, 2L));
        eqANode = Node.create(Address.create(device,3L));
    }

    /*
        Sink Registration
     */

    @Test
    public void it_should_should_add_sink_to_nodeMap() throws Exception {
        Address sa = Address.create(device,1234L);
        when(addRegService.registerSinkAddress(anyLong(),eq(device))).thenReturn(sa);

        Node sink = nodeRegistrationService.registerSink(1234L,device);
        assertThat(nodeMap.contains(sink),is(true));
    }

    @Test
    public void it_should_add_sink_to_registeredDevices() throws Exception {
        Address sa = Address.create(device,1234L);
        when(addRegService.registerSinkAddress(anyLong(),eq(device))).thenReturn(sa);

        Node sink = nodeRegistrationService.registerSink(1234L,device);
        assertThat(registeredNodes.containsKey(sink),is(true));
    }

    @Test
    public void it_should_add_sink_with_ACTIVE_status() throws Exception {
        Address sa = Address.create(device,1234L);
        when(addRegService.registerSinkAddress(anyLong(),eq(device))).thenReturn(sa);

        Node sink = nodeRegistrationService.registerSink(1234L,device);
        Node actNode = registeredNodes.get(sink);
        assertThat(actNode.getStatus(),is(equalTo(ACTIVE)));
    }

    @Test
    public void it_should_add_srcNode_to_registeredNodes() {
        nodeRegistrationService.registerSrcDstNodesInPacket(srcNode, dstNode);
        assertThat(registeredNodes.containsKey(srcNode), is(true));
    }

    @Test
    public void it_should_add_dstNode_to_registeredNodes() {
        nodeRegistrationService.registerSrcDstNodesInPacket(srcNode, dstNode);
        assertThat(registeredNodes.containsKey(dstNode), is(true));
    }

    @Test
    public void it_should_change_srcNode_status_to_ACTIVE() throws Exception {
        nodeRegistrationService.registerSrcDstNodesInPacket(srcNode, dstNode);
        Node actNode = registeredNodes.get(eqSrcNode);

        assertThat(actNode.getStatus(), is(equalTo(ACTIVE)));
    }

    @Test
    public void it_should_change_dstNode_status_to_IDLE() throws Exception {
        nodeRegistrationService.registerSrcDstNodesInPacket(srcNode, dstNode);
        Node actNode = registeredNodes.get(eqDstNode);

        assertThat(actNode.getStatus(), is(equalTo(IDLE)));
    }

    @Test
    public void it_should_update_srcNode_status_to_ACTIVE_if_it_is_already_registered() throws Exception {
        nodeRegistrationService.registerSrcDstNodesInPacket(srcNode, dstNode);

        Node actNode = registeredNodes.get(eqDstNode);
        assertThat(actNode.getStatus(), is(equalTo(IDLE)));
        nodeRegistrationService.registerSrcDstNodesInPacket(dstNode, srcNode);

        assertThat(actNode.getStatus(), is(equalTo(ACTIVE)));
    }

    /*
        NodeMap
     */

    //The process of adding to map happens for both src and dst
    @Test
    public void it_should_add_registered_nodes_to_nodeMap() throws Exception {
        nodeRegistrationService.registerSrcDstNodesInPacket(srcNode, dstNode);

        assertThat(nodeMap.contains(srcNode),is(true));

        assertThat(nodeMap.contains(dstNode),is(true));
    }

    /*
        Registering Neighbors
     */

    @Test
    public void it_should_add_neighbors_to_NodeMap() throws Exception {
        assertFalse(nodeMap.contains(srcNode));
        Set<Link> links = createLinks(5,device);
        nodeRegistrationService.registerNeighbors(links);

        links.forEach(n->{
            Node neighbor = n.getDstNode();
            assertThat(nodeMap.contains(neighbor),is(true));
        });
    }

    //Default value for Node during instantiation is UNREGISTERED
    //This test will pass if you remove set status logic from registerNeighbors method
    @Test
    public void it_should_add_new_neighbor_nodes_as_UNREGISTERED() throws Exception {
        assertFalse(nodeMap.contains(srcNode));
        Set<Link> links = createLinks(5,device);
        nodeRegistrationService.registerNeighbors(links);

        links.forEach(n->{
            Node neighbor = n.getDstNode();
            assertThat(neighbor.getStatus(),is(equalTo(UNREGISTERED)));
        });
    }

    @Test
    public void it_should_add_neighbors_to_nodeMap_ONLY_() throws Exception {
        assertFalse(nodeMap.contains(srcNode));
        Set<Link> links = createLinks(5,device);
        nodeRegistrationService.registerNeighbors(links);

        links.forEach(n->{
            Node neighbor = n.getDstNode();
            assertThat(registeredNodes.containsKey(n),is(false));
        });
    }

    @Test
    public void it_should_leave_a_node_in_registeredNodes_if_it_is_already_registered() throws Exception {
        assertFalse(nodeMap.contains(srcNode));
        Set<Link> links = createLinks(5,device);

        nodeRegistrationService.registerSrcDstNodesInPacket(srcNode,dstNode);

        nodeRegistrationService.registerNeighbors(links);

        assertThat(registeredNodes.containsKey(eqSrcNode),is(true));
        assertThat(registeredNodes.containsKey(eqANode),is(false));
    }

    private Set<Link> createLinks(int num, Device device){
        Set<Link> links = new HashSet<>();
        for (int i = 0; i < num; i++) {
            Link link = new Link(
                    Node.create(
                            Address.create(device,Integer.toUnsignedLong(i))
                    )
            );
            links.add(link);
        }

        return links;
    }
}