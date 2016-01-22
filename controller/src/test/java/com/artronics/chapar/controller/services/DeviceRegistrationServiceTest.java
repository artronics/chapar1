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

import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class DeviceRegistrationServiceTest extends BaseControllerTest{

    @InjectMocks
    private DeviceRegistrationServiceImpl registrationService;

    @Mock
    private DeviceRepo deviceRepo;

//    @Resource("registeredDevices")
    @Mock
    private Map<Device,DeviceMap> registeredDevices;

    private Device device;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        device = new Device();
        device.setId(1L);
//        when(deviceRepo.save())
    }
    @Test
    public void it_should_add_registered_device_in_registeredDevices(){

    }
    @Test
    public void it_should_persist_device(){
        registrationService.registerDevice(device);
        verify(deviceRepo,times(1)).save(device);
    }

}