package com.artronics.chapar.client.services.impl;

import com.artronics.chapar.client.events.RxBufferReadyEvent;
import com.artronics.chapar.client.events.TxBuffersReadyEvent;
import com.artronics.chapar.client.services.ClientBufferService;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.BufferRepo;
import com.artronics.chapar.domain.repositories.TimeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

public abstract class BaseClientBufferService implements ClientBufferService {
    private final static Logger log = Logger.getLogger(BaseClientBufferService.class);

    protected ApplicationEventPublisher publisher;

    protected Client registeredClient;

    protected BufferRepo bufferRepo;
    protected TimeRepo timeRepo;

    @EventListener
    public void rxBufferReadyHandler(RxBufferReadyEvent e) {
        Buffer b = e.getBuffer();

        sendBufferToController(b, Buffer.Direction.RX);
    }

    @Override
    public Buffer sendBufferToSink(Buffer buffer) {
        TxBuffersReadyEvent e = new TxBuffersReadyEvent(this, buffer);
        publisher.publishEvent(e);

        return buffer;
    }

    protected void persistBuffer(Buffer buffer, Buffer.Direction dir) {
        if (registeredClient.getId() == null) {
            log.warn("Try to persist a buffer with an unregistered Client.");
        }
        buffer.setClient(registeredClient);

        if (dir == Buffer.Direction.RX)
            buffer.setReceivedAt(timeRepo.getDbNowTime());

        buffer.setDirection(dir);

        bufferRepo.save(buffer);
    }

    @Override
    public void setRegisteredClient(Client registeredClient) {
        this.registeredClient = registeredClient;
    }

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Autowired
    public void setBufferRepo(BufferRepo bufferRepo) {
        this.bufferRepo = bufferRepo;
    }

    @Autowired
    public void setTimeRepo(TimeRepo timeRepo) {
        this.timeRepo = timeRepo;
    }

}
