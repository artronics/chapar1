package com.artronics.chapar.device.driver.fake;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.device.driver.DeviceDriver;
import com.artronics.chapar.device.events.BufferReadyEvent;
import com.artronics.chapar.device.exceptions.DeviceDriverException;
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
        isStarted = true;
        try {
            while (isStarted)
            {
                Buffer buffer = new Buffer(Arrays.asList(10,1,0,30,0,0,2,0,0,10));
                BufferReadyEvent e = new BufferReadyEvent(this,buffer);
                log.debug("Sending Fake Buffer");
                publisher.publishEvent(e);

                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws DeviceDriverException {
        isStarted = false;
    }
}
