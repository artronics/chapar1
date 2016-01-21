package com.artronics.chapar.device.client.http;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.device.client.BufferDispatcher;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("httpBufferDispatcher")
public class BufferDispatcherHttpClient implements BufferDispatcher{
    private final static Logger log = Logger.getLogger(BufferDispatcherHttpClient.class);

    @Override
    public void sendBuffer(Buffer buffer) {

    }

}
