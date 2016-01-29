package com.artronics.chapar.controller.services;

import com.artronics.chapar.controller.entities.Client;
import com.artronics.chapar.controller.http.controllers.BaseControllerTest;
import com.artronics.chapar.controller.repositories.DeviceRepo;
import com.artronics.chapar.controller.services.impl.DeviceRegistrationServiceImpl;
import com.artronics.chapar.domain.map.NetworkMap;
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


public class ClientRegistrationServiceTest extends BaseControllerTest{

    @InjectMocks
    private DeviceRegistrationServiceImpl registrationService;

    @Mock
    private DeviceRepo deviceRepo;

    private Map<Client,NetworkMap> registeredDevices;

    private Client client;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        registeredDevices = new HashMap<>();
        registrationService.setRegisteredDevices(registeredDevices);

        client = new Client();
        client.setId(1L);
        when(deviceRepo.save(client)).thenReturn(client);
    }

    @Test
    public void it_should_add_registered_device_in_registeredDevices(){
        registrationService.registerDevice(client);
        assertTrue(registeredDevices.containsKey(client));
    }

    @Test
    public void it_should_create_new_DeviceMap_for_each_device(){
        Client client2 = new Client();
        client2.setId(2L);
        when(deviceRepo.save(client2)).thenReturn(client2);

        registrationService.registerDevice(client);
        registrationService.registerDevice(client2);

        assertThat(registeredDevices.size(),equalTo(2));

        NetworkMap map1 = registeredDevices.get(client);
        NetworkMap map2 = registeredDevices.get(client2);
        assertThat(map1,is(not(map2)));
    }

    @Test
    public void it_should_persist_device(){
        registrationService.registerDevice(client);
        verify(deviceRepo,times(1)).save(client);
    }

}