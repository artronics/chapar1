package com.artronics.chapar.device.events;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;

public class BaseEvent<T> extends ApplicationEvent{
    private final static Logger log = Logger.getLogger(BaseEvent.class);

    private final T message;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public BaseEvent(Object source,T message) {
        super(source);
        this.message = message;
    }
}
