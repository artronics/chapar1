package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.map.NodeMap;
import com.artronics.chapar.core.map.NodeMapImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static com.artronics.chapar.core.entities.Node.Status.ACTIVE;
import static com.artronics.chapar.core.entities.Node.Status.IDLE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class NodeRegistrationServiceImplTest {

    @InjectMocks
    private NodeRegistrationServiceImpl nodeRegistrationService;

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

    @Test
    public void it_should_add_srcNode_to_registeredNodes() {
        nodeRegistrationService.registerNode(srcNode, dstNode);
        assertThat(registeredNodes.containsKey(srcNode), is(true));
    }

    @Test
    public void it_should_add_dstNode_to_registeredNodes() {
        nodeRegistrationService.registerNode(srcNode, dstNode);
        assertThat(registeredNodes.containsKey(dstNode), is(true));
    }

    @Test
    public void it_should_change_srcNode_status_to_ACTIVE() throws Exception {
        nodeRegistrationService.registerNode(srcNode, dstNode);
        Node actNode = registeredNodes.get(eqSrcNode);

        assertThat(actNode.getStatus(), is(equalTo(ACTIVE)));
    }

    @Test
    public void it_should_change_dstNode_status_to_IDLE() throws Exception {
        nodeRegistrationService.registerNode(srcNode, dstNode);
        Node actNode = registeredNodes.get(eqDstNode);

        assertThat(actNode.getStatus(), is(equalTo(IDLE)));
    }

    @Test
    public void it_should_update_srcNode_status_to_ACTIVE_if_it_is_already_registered() throws Exception {
        nodeRegistrationService.registerNode(srcNode, dstNode);

        Node actNode = registeredNodes.get(eqDstNode);
        assertThat(actNode.getStatus(), is(equalTo(IDLE)));
        nodeRegistrationService.registerNode(dstNode, srcNode);

        assertThat(actNode.getStatus(), is(equalTo(ACTIVE)));
    }

    /*
        NodeMap
     */

    //The process of adding to map happens for both src and dst
    @Test
    public void it_should_add_registered_nodes_to_nodeMap() throws Exception {
        nodeRegistrationService.registerNode(srcNode, dstNode);

        assertThat(nodeMap.contains(srcNode),is(true));

        assertThat(nodeMap.contains(dstNode),is(true));
    }

    /*
        Registering Neighbors
     */

    @Test
    public void it_should_add_neighbors_to_NodeMap() throws Exception {
        assertFalse(nodeMap.contains(srcNode));
        Set<Node> neighbors = new HashSet<>(Arrays.asList(srcNode,dstNode,aNode));
        nodeRegistrationService.registerNeighbors(neighbors);

        neighbors.forEach(n->{
            assertThat(nodeMap.contains(n),is(true));
        });
    }

    @Test
    public void it_should_register_new_neighbors_in_map_with_UNREGISTERED_status() throws Exception {
        assertFalse(nodeMap.contains(srcNode));
        Set<Node> neighbors = new HashSet<>(Arrays.asList(srcNode,dstNode,aNode));
        nodeRegistrationService.registerNeighbors(neighbors);



    }
}