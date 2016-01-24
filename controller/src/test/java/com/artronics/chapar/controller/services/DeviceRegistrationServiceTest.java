package com.artronics.chapar.controller.services;

import com.artronics.chapar.controller.services.impl.BaseServiceTest;
import com.artronics.chapar.controller.services.impl.DeviceRegistrationServiceImpl;
import com.artronics.chapar.core.repositories.DeviceRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class DeviceRegistrationServiceTest extends BaseServiceTest{

    @InjectMocks
    private DeviceRegistrationServiceImpl registrationService;

    @Mock
    private DeviceRepo deviceRepo;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        registrationService.setRegisteredDevices(registeredDevices);

        when(deviceRepo.save(device)).thenReturn(device);
    }

    @Test
    public void it_should_add_registered_device_in_registeredDevices(){
        registrationService.registerDevice(device);
        assertTrue(registeredDevices.containsKey(device.getId()));
    }

    @Test
    public void it_should_persist_device(){
        registrationService.registerDevice(device);
        verify(deviceRepo,times(1)).save(device);
    }

}