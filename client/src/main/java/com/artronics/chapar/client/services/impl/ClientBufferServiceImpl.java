package com.artronics.chapar.client.services.impl;

import com.artronics.chapar.client.events.RxBufferReadyEvent;
import com.artronics.chapar.client.events.TxBuffersReadyEvent;
import com.artronics.chapar.client.services.ClientBufferService;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.BufferRepo;
import com.artronics.chapar.domain.repositories.TimeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Service
public class ClientBufferServiceImpl implements ClientBufferService{
    private final static Logger log = Logger.getLogger(ClientBufferServiceImpl.class);

    private volatile boolean isStarted = false;
    private ApplicationEventPublisher publisher;
    private Long deviceTxRate;

    private BufferRepo bufferRepo;

    private TimeRepo timeRepo;

    private Client registeredClient;

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
    public Buffer sendBuffer(Buffer buffer) {
        if (registeredClient.getId() == null) {
            log.warn("Try to persist a buffer with an unregistered Client.");
        }
        buffer.setClient(registeredClient);

        buffer.setReceivedAt(timeRepo.getDbNowTime());
        buffer.setDirection(Buffer.Direction.RX);

        bufferRepo.save(buffer);

        log.debug(Buffer.soutBuffer(buffer));

        return buffer;
    }

    @Scheduled(fixedRateString = "${com.artronics.chapar.client.buffer.scheduler.availability.rate}")
    public void checkForNewBuffersFromController(){
        log.debug("Checking for new buffers from controller.");

        List<Buffer> buffers = bufferRepo.getReadyTxBuffers(registeredClient);

        if (!buffers.isEmpty()) {
            txBufferQueue.addAll(buffers);
        }
    }

    @EventListener
    public void rxBufferReadyHandler(RxBufferReadyEvent e){
        Buffer b= e.getBuffer();

        sendBuffer(b);
    }

    @Autowired
    public void setBufferRepo(BufferRepo bufferRepo) {
        this.bufferRepo = bufferRepo;
    }

    @Autowired
    public void setTimeRepo(TimeRepo timeRepo) {
        this.timeRepo = timeRepo;
    }

    @Override
    @Resource(name = "registeredClient")
    public void setRegisteredClient(Client registeredClient) {
        this.registeredClient = registeredClient;
    }

    @Resource(name = "txBufferQueue")
    public void setTxBufferQueue(BlockingQueue<Buffer> txBufferQueue) {
        this.txBufferQueue = txBufferQueue;
    }

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
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

                        log.debug(Buffer.soutBuffer(buffer));
                        TxBuffersReadyEvent e = new TxBuffersReadyEvent(this, buffer);
                        publisher.publishEvent(e);

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
