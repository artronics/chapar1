package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.DeviceNotRegistered;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

public class PacketServiceImplTest {
    
    @InjectMocks
    private PacketServiceImpl packetService;

    protected Map<Long,Device> registeredDevices;

    protected Device device = new Device();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        registeredDevices = new HashMap<>();
        packetService.setRegisteredDevices(registeredDevices);

        device.setId(1L);
        registeredDevices.put(device.getId(),device);
    }

    @Test(expected = DeviceNotRegistered.class)
    public void it_should_throw_exp_if_device_is_not_registered() throws MalformedPacketException {
        packetService.addPacket(new Packet(),442L);
    }

}