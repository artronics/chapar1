package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.map.NetworkStructure;
import com.artronics.chapar.domain.repositories.ClientRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ClientRegistrationServiceImplTest {

    @InjectMocks
    private ClientRegistrationServiceImpl registrationService;

    @Mock
    private ClientRepo clientRepo;
    @Mock
    private NetworkStructure networkStructure;

    private Map<Client,Client> registeredClients;

    private Client client;
    private Controller controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        client = new Client();
        controller = new Controller(1L);

        registeredClients = new HashMap<>();
        registrationService.setRegisteredClients(registeredClients);
    }

    @Test
    public void it_should_set_Client_Controller() throws Exception {
        when(networkStructure.getController()).thenReturn(controller);
        when(clientRepo.save(any(Client.class))).thenReturn(client);

        ArgumentCaptor<Client> ac = ArgumentCaptor.forClass(Client.class);

        registrationService.registerDevice(client);

        verify(clientRepo).save(ac.capture());


        Client cClient = ac.getValue();
        assertThat(cClient.getController(),is(equalTo(controller)));
    }

    @Test
    public void it_should_persist_client(){
        when(clientRepo.save(any(Client.class))).thenReturn(client);
        registrationService.registerDevice(client);
        verify(clientRepo,times(1)).save(client);
    }

    @Test
    public void it_should_add_client_to_registeredDevices() throws Exception {
        when(clientRepo.save(any(Client.class))).thenReturn(client);
        registrationService.registerDevice(client);

        assertThat(registeredClients.size(),is(equalTo(1)));
    }
}