package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.DeviceNotRegistered;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import com.artronics.chapar.core.map.DeviceMap;
import com.artronics.chapar.core.map.DeviceMapImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

public class BaseServiceTest {

    protected Map<Device,DeviceMap> registeredDevices;

    protected Device device = new Device();
    protected DeviceMap deviceMap = new DeviceMapImpl();
    protected Buffer buffer;
    protected Packet packet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        device.setId(1L);
        registeredDevices = new HashMap<>();
        registeredDevices.put(device,deviceMap);
    }

    @Test(expected = DeviceNotRegistered.class)
    public void it_should_throw_exp_if_device_is_not_registered() throws MalformedPacketException {
        BaseService baseService = new BaseService();
        baseService.setRegisteredDevices(registeredDevices);
        baseService.checkDevice(234L);
    }

}
