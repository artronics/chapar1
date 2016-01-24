package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.DeviceNotRegistered;
import com.artronics.chapar.core.exceptions.NodeConflictException;
import com.artronics.chapar.core.map.DeviceMap;
import com.artronics.chapar.core.map.DeviceMapImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NodeRegistrationServiceImplTest {

    @InjectMocks
    private NodeRegistrationServiceImpl nodeRegistrationService;

    private Map<Device,DeviceMap> registeredDevices;
    private DeviceMap deviceMap= new DeviceMapImpl();
    private Device device;

    private Node node = new Node(10L);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        device = new Device();
        device.setId(1L);
        registeredDevices = new HashMap<>();
        registeredDevices.put(device,deviceMap);

        nodeRegistrationService.setRegisteredDevices(registeredDevices);
    }

    @Test(expected = DeviceNotRegistered.class)
    public void it_should_throw_exp_if_device_is_not_registered(){
        nodeRegistrationService.registerNode(new Device(),node);
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