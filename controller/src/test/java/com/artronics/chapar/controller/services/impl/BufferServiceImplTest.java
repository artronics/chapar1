package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.exceptions.DeviceNotRegistered;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import com.artronics.chapar.core.map.DeviceMap;
import com.artronics.chapar.core.map.DeviceMapImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BufferServiceImplTest {

    @InjectMocks
    private BufferServiceImpl bufferService;

    private Map<Device,DeviceMap> registeredDevices;

    private Device device = new Device();
    private DeviceMap deviceMap = new DeviceMapImpl();
    private Buffer buffer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        device.setId(1L);
        registeredDevices = new HashMap<>();
        registeredDevices.put(device,deviceMap);

        bufferService.setRegisteredDevices(registeredDevices);
    }

    @Test(expected = DeviceNotRegistered.class)
    public void it_should_throw_exp_if_device_is_not_registered() throws MalformedPacketException {
        buffer = new Buffer(Arrays.asList(1,2,3,4,5));
        bufferService.addBuffer(234L,buffer);
    }

}