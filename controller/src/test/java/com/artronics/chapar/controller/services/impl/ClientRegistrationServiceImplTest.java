package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.entities.Client;
import com.artronics.chapar.controller.repositories.ClientRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ClientRegistrationServiceImplTest {

    @InjectMocks
    private ClientRegistrationServiceImpl registrationService;

    @Mock
    private ClientRepo clientRepo;

    private Client client;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        client = new Client();

        when(clientRepo.save(any(Client.class))).thenReturn(client);
    }

    @Test
    public void it_should_persist_device(){
        registrationService.registerDevice(client);
        verify(clientRepo,times(1)).save(client);
    }

}