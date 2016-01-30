package com.artronics.chapar.client.events;

import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class TxBuffersReadyEvent extends ApplicationEvent{
    private final static Logger log = Logger.getLogger(TxBuffersReadyEvent.class);

    private List<Buffer> buffers;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public TxBuffersReadyEvent(Object source,List<Buffer> buffers) {
        super(source);
        this.buffers = buffers;
    }

    public List<Buffer> getBuffers() {
        return buffers;
    }

}
