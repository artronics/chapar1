package com.artronics.chapar.client.driver.buffer;

import com.artronics.chapar.client.events.RxBufferReadyEvent;
import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class BufferDistributorImpl implements BufferDistributor
{
    private final static Logger log = Logger.getLogger(BufferDistributorImpl.class);
    private static final int MAX_PACKET_LENGTH = 255;

    private InputStream input;

    private OutputStream output;

    private BufferCollector bufferCollector;

    private ApplicationEventPublisher publisher;

    @Override
    public void bufferReceived()
    {
        try {

            final byte[] buff = new byte[MAX_PACKET_LENGTH];
            final int length = input.read(buff, 0, MAX_PACKET_LENGTH);
            final ArrayList<Integer> intBuff = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                //convert signed value to unsigned
                intBuff.add(buff[i] & 0xFF);
            }

            collectBuff(intBuff);

        }catch (IOException e) {
            e.printStackTrace();
            log.error("IO exp while reading buffer from Device Driver.");
        }

    }

    private void collectBuff(List<Integer> message)
    {
        List<List<Integer>> buffers = bufferCollector.generateLists(message);
        if (buffers.isEmpty()) {
            return;
        }

        log.debug("This buffer contains " + buffers.size() + " packets");

        for (List<Integer> buff : buffers) {
            log.debug("Sending buffer");
            Buffer buffer = new Buffer(buff);
            RxBufferReadyEvent e = new RxBufferReadyEvent(this,buffer);

            publisher.publishEvent(e);
        }
    }

    @Override
    public void setInput(InputStream input)
    {
        this.input = input;
    }

    @Override
    public void setOutput(OutputStream output) {
        this.output = output;
    }

    @Autowired
    public void setBufferCollector(BufferCollector bufferCollector)
    {
        this.bufferCollector = bufferCollector;
    }

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

}
