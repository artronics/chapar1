package com.artronics.chapar.client.events;

import com.artronics.chapar.domain.model.Buffer;
import org.apache.log4j.Logger;

public class BufferReadyEvent extends BaseEvent<Buffer>{
    private final static Logger log = Logger.getLogger(BufferReadyEvent.class);

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public BufferReadyEvent(Object source,Buffer buffer) {
        super(source,buffer);
    }
}
