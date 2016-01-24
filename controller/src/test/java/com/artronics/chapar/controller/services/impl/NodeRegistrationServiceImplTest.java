package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.NodeConflictException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;

public class NodeRegistrationServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private NodeRegistrationServiceImpl nodeRegistrationService;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        node = Node.create(Address.create(device,10L));

        nodeRegistrationService.setRegisteredDevices(registeredDevices);
    }

    @Ignore("refactor")
    @Test
    public void it_should_add_node_to_registeredDevices(){
        nodeRegistrationService.registerNode(device,node);
//        assertThat(deviceMap.contains(node),is(true));
    }

    @Ignore("refactor")
    @Test(expected = NodeConflictException.class)
    public void it_should_throw_exp_if_node_is_already_registered(){
        nodeRegistrationService.registerNode(device,node);
        nodeRegistrationService.registerNode(device,node);
    }
}