package com.artronics.chapar.client.services.impl;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.BufferRepo;
import com.artronics.chapar.domain.repositories.TimeRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ClientBufferServiceImplTest {
    @InjectMocks
    private ClientBufferServiceImpl bufferService;

    @Mock
    private BufferRepo bufferRepo;

    @Mock
    private TimeRepo timeRepo;

    private Client registeredClient;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        registeredClient = new Client();
        registeredClient.setId(1L);

        bufferService.setRegisteredClient(registeredClient);

        when(timeRepo.getDbNowTime()).thenReturn(new Timestamp(new Date().getTime()));
    }

    @Test
    public void it_should_set_direction_RX_if_call_to_send_buffer() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5,6,7));
        bufferService.sendBuffer(b);

        assertThat(b.getDirection(),is(equalTo(Buffer.Direction.RX)));
    }

    @Test
    public void it_should_add_registeredClient_to_buffer() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5,6,7));
        bufferService.sendBuffer(b);

        assertThat(b.getClient(),is(equalTo(registeredClient)));
    }

    @Test
    public void it_should_add_receivedAt_field() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5,6,7));
        bufferService.sendBuffer(b);

        assertThat(b.getReceivedAt(),is(notNullValue()));
    }

    @Test
    public void it_should_persist_buffer() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5,6,7));
        bufferService.sendBuffer(b);

        verify(bufferRepo,times(1)).save(b);
        verifyNoMoreInteractions(bufferRepo);
    }

}