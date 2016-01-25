package com.artronics.chapar.controller.services;

import com.artronics.chapar.controller.services.impl.DeviceRegistrationServiceImpl;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.repositories.DeviceRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class DeviceRegistrationServiceTest {

    @InjectMocks
    private DeviceRegistrationServiceImpl registrationService;

    @Mock
    private DeviceRepo deviceRepo;

    protected Map<Long,Device> registeredDevices;
    protected Device device = new Device();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        registeredDevices = new HashMap<>();
        registrationService.setRegisteredDevices(registeredDevices);

        when(deviceRepo.save(any(Device.class))).thenReturn(device);
    }

    @Test
    public void it_should_add_registered_device_in_registeredDevices(){
        registrationService.registerDevice(new Device());
        assertTrue(registeredDevices.containsKey(device.getId()));
    }

    @Test
    public void it_should_persist_device(){
        registrationService.registerDevice(device);
        verify(deviceRepo,times(1)).save(device);
    }

}