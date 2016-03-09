package com.artronics.chapar.client.services.impl;

import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BlockingQueue;

//@Component("clientBufferServiceImpl")
public class ClientBufferServiceImpl extends BaseClientBufferService {
    private final static Logger log = Logger.getLogger(ClientBufferServiceImpl.class);

    private volatile boolean isStarted = false;
    private Long deviceTxRate;

    private BlockingQueue<Buffer> txBufferQueue;

    public ClientBufferServiceImpl() {
    }

    @Override
    public void start() {
        isStarted = true;
        Thread t= new Thread(new BufferTxListener(),"TX_LSN");
        t.start();
    }

    @Override
    public void stop() {
        isStarted = false;
    }

    @Override
    public Buffer sendBufferToController(Buffer buffer) {
        persistBuffer(buffer, Buffer.Direction.RX);

        log.debug(Buffer.soutBuffer(buffer));

        return buffer;
    }

    @Override
    public Buffer sendBufferToController(Buffer buffer, Buffer.Direction dir) {
        return sendBufferToController(buffer);
    }

    //    @Scheduled(fixedRateString = "${com.artronics.chapar.client.buffer.scheduler.availability.rate}")
    public void checkForNewBuffersFromController(){
        log.debug("Checking for new buffers from controller.");

        List<Buffer> buffers = bufferRepo.getReadyTxBuffers(registeredClient);

        if (!buffers.isEmpty()) {
            txBufferQueue.addAll(buffers);
        }
    }

    @Resource(name = "txBufferQueue")
    public void setTxBufferQueue(BlockingQueue<Buffer> txBufferQueue) {
        this.txBufferQueue = txBufferQueue;
    }

    @Value("${com.artronics.chapar.client.driver.tx_rate}")
    public void setDeviceTxRate(Long deviceTxRate) {
        this.deviceTxRate = deviceTxRate;
    }

    private class BufferTxListener implements Runnable{

        @Override
        public void run() {
            while (isStarted){
                while (!txBufferQueue.isEmpty()){
                    try {
                        Buffer buffer = txBufferQueue.take();
                        buffer.setSentAt(timeRepo.getDbNowTime());
                        bufferRepo.save(buffer);

                        sendBufferToSink(buffer);

                        Thread.sleep(deviceTxRate);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(deviceTxRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
