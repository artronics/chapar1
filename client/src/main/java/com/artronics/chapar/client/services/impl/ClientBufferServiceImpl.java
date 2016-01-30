package com.artronics.chapar.client.services.impl;

import com.artronics.chapar.client.events.RxBufferReadyEvent;
import com.artronics.chapar.client.services.ClientBufferService;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.BufferRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClientBufferServiceImpl implements ClientBufferService{
    private final static Logger log = Logger.getLogger(ClientBufferServiceImpl.class);

    private BufferRepo bufferRepo;

    private Client registeredClient;

    @Override
    public Buffer sendBuffer(Buffer buffer) {
        if (registeredClient.getId() == null) {
            log.warn("Try to persist a buffer with an unregistered Client.");
        }
        buffer.setClient(registeredClient);

        buffer.setDirection(Buffer.Direction.RX);

        bufferRepo.save(buffer);

        log.debug("New Buffer persisted.");

        return buffer;
    }

    @EventListener
    public void rxBufferReadyHandler(RxBufferReadyEvent e){
        Buffer b= e.getBuffer();

        sendBuffer(b);
    }

    @Autowired
    public void setBufferRepo(BufferRepo bufferRepo) {
        this.bufferRepo = bufferRepo;
    }

    @Override
    @Resource(name = "registeredClient")
    public void setRegisteredClient(Client registeredClient) {
        this.registeredClient = registeredClient;
    }

}
