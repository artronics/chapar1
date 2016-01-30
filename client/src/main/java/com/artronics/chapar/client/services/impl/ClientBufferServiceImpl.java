package com.artronics.chapar.client.services.impl;

import com.artronics.chapar.client.events.RxBufferReadyEvent;
import com.artronics.chapar.client.repositories.ClientBufferRepo;
import com.artronics.chapar.client.services.ClientBufferService;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import org.apache.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClientBufferServiceImpl implements ClientBufferService{
    private final static Logger log = Logger.getLogger(ClientBufferServiceImpl.class);

    private ClientBufferRepo bufferRepo;

    private Client registeredClient;

    @Override
    public void sendBuffer(Buffer buffer) {


    }

    @EventListener
    public void rxBufferReadyHandler(RxBufferReadyEvent e){
        Buffer b= e.getBuffer();
        b.setDirection(Buffer.Direction.RX);

        sendBuffer(b);
    }

    @Resource(name = "registeredClient")
    public void setRegisteredClient(Client registeredClient) {
        this.registeredClient = registeredClient;
    }

}
