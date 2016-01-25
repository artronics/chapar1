package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.exceptions.DeviceNotRegistered;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

public class BufferServiceImplTest {

    @InjectMocks
    private BufferServiceImpl bufferService;

    protected Map<Long,Device> registeredDevices;

    protected Device device = new Device();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        device.setId(1L);
        registeredDevices = new HashMap<>();
        registeredDevices.put(device.getId(),device);
        bufferService.setRegisteredDevices(registeredDevices);
    }

    @Test(expected = DeviceNotRegistered.class)
    public void it_should_throw_exp_if_device_is_not_registered() throws Exception {
        bufferService.addBuffer(442L,new Buffer());
    }

}