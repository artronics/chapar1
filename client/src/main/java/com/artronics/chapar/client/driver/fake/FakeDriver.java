package com.artronics.chapar.client.driver.fake;

import com.artronics.chapar.client.driver.DeviceDriver;
import com.artronics.chapar.client.events.BufferReadyEvent;
import com.artronics.chapar.client.exceptions.DeviceDriverException;
import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("fakeDeviceDriver")
public class FakeDriver implements DeviceDriver{
    private final static Logger log = Logger.getLogger(FakeDriver.class);

    private boolean isStarted = false;

    @Autowired
    ApplicationEventPublisher publisher;

    @Override
    public void init() {
        log.debug("Initializing FAKE Driver. It sends buffer periodically");
    }

    @Override
    public void open() throws DeviceDriverException {
        Thread t = new Thread(new TX());
        t.start();
    }

    @Override
    public void close() throws DeviceDriverException {
        isStarted = false;
    }

    class TX implements Runnable{

        @Override
        public void run() {
            isStarted = true;
            try {
                while (isStarted)
                {
                    Buffer buffer = new Buffer(Arrays.asList(1,2,3,4,5,6));
                    BufferReadyEvent e = new BufferReadyEvent(this,buffer);
                    log.debug("Sending Fake Buffer");
                    publisher.publishEvent(e);

                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
