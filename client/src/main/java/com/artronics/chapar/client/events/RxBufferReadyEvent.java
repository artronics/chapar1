package com.artronics.chapar.client.events;

import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;

public class RxBufferReadyEvent extends ApplicationEvent{
    private final static Logger log = Logger.getLogger(RxBufferReadyEvent.class);

    private Buffer buffer;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public RxBufferReadyEvent(Object source, Buffer buffer) {
        super(source);
        this.buffer = buffer;
    }

    public Buffer getBuffer() {
        return buffer;
    }
}