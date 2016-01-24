package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.NodeConflictException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NodeRegistrationServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private NodeRegistrationServiceImpl nodeRegistrationService;

    private Node node = new Node(10L);

    @Before
    public void setUp() throws Exception {
        super.setUp();

        nodeRegistrationService.setRegisteredDevices(registeredDevices);
    }

    @Test
    public void it_should_add_node_to_registeredDevices(){
        nodeRegistrationService.registerNode(device,node);
        assertThat(deviceMap.contains(node),is(true));
    }

    @Test(expected = NodeConflictException.class)
    public void it_should_throw_exp_if_node_is_already_registered(){
        nodeRegistrationService.registerNode(device,node);
        nodeRegistrationService.registerNode(device,node);
    }
}