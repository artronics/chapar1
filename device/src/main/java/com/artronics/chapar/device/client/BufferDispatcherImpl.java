package com.artronics.chapar.device.client;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.device.events.BufferReadyEvent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BufferDispatcherImpl implements BufferDispatcher{
    private final static Logger log = Logger.getLogger(BufferDispatcherImpl.class);

    BufferDispatcher httpBufferDispatcher;

    @Override
    public void sendBuffer(Buffer buffer) {
        httpBufferDispatcher.sendBuffer(buffer);
    }

    @EventListener
    public void getBuffer(BufferReadyEvent e){
        Buffer b = e.getMessage();
        sendBuffer(b);
    }

    @Autowired
    @Qualifier("httpBufferDispatcher")
    public void setHttpBufferDispatcher(BufferDispatcher httpBufferDispatcher) {
        this.httpBufferDispatcher = httpBufferDispatcher;
    }
}
