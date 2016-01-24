package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.DeviceNotRegistered;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

public class BaseServiceTest {

    protected Set<Device> registeredDevices;

    protected Device device = new Device();
    protected Buffer buffer;
    protected Packet packet;
    protected Node node;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        device.setId(1L);
        registeredDevices = new HashSet<>();
        registeredDevices.add(device);
    }

    @Test(expected = DeviceNotRegistered.class)
    public void it_should_throw_exp_if_device_is_not_registered() throws MalformedPacketException {
        BaseService baseService = new BaseService();
        baseService.setRegisteredDevices(registeredDevices);
        baseService.checkDevice(234L);
    }

}
