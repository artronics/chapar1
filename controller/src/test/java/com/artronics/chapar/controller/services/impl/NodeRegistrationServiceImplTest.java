package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static com.artronics.chapar.core.entities.Node.Status.ACTIVE;
import static com.artronics.chapar.core.entities.Node.Status.IDLE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NodeRegistrationServiceImplTest {

    @InjectMocks
    private NodeRegistrationServiceImpl nodeRegistrationService;

    private Map<Node, Node> registeredNodes;

    private Device device;
    private Node srcNode;
    private Node dstNode;
    private Node eqSrcNode;
    private Node eqDstNode;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        registeredNodes = new HashMap<>();
        nodeRegistrationService.setRegisteredNodes(registeredNodes);

        device = new Device(1L);
        srcNode = Node.create(Address.create(device, 1L));
        dstNode = Node.create(Address.create(device, 2L));

        eqSrcNode = Node.create(Address.create(device, 1L));
        eqDstNode = Node.create(Address.create(device, 2L));
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

}