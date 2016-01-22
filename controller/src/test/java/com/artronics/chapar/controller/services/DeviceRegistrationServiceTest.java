package com.artronics.chapar.controller.services;

import com.artronics.chapar.controller.BaseControllerTest;
import com.artronics.chapar.controller.services.impl.DeviceRegistrationServiceImpl;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.map.DeviceMap;
import com.artronics.chapar.core.repositories.DeviceRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class DeviceRegistrationServiceTest extends BaseControllerTest{

    @InjectMocks
    private DeviceRegistrationServiceImpl registrationService;

    @Mock
    private DeviceRepo deviceRepo;

    private Map<Device,DeviceMap> registeredDevices;

    private Device device;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        registeredDevices = new HashMap<>();
        registrationService.setRegisteredDevices(registeredDevices);

        device = new Device();
        device.setId(1L);
        when(deviceRepo.save(device)).thenReturn(device);
    }

    @Test
    public void it_should_add_registered_device_in_registeredDevices(){
        registrationService.registerDevice(device);
        assertTrue(registeredDevices.containsKey(device));
    }

    @Test
    public void it_should_create_new_DeviceMap_for_each_device(){
        Device device2 = new Device();
        device2.setId(2L);
        when(deviceRepo.save(device2)).thenReturn(device2);

        registrationService.registerDevice(device);
        registrationService.registerDevice(device2);

        assertThat(registeredDevices.size(),equalTo(2));

        DeviceMap map1 = registeredDevices.get(device);
        DeviceMap map2 = registeredDevices.get(device2);
        assertThat(map1,is(not(map2)));
    }

    @Test
    public void it_should_persist_device(){
        registrationService.registerDevice(device);
        verify(deviceRepo,times(1)).save(device);
    }

}