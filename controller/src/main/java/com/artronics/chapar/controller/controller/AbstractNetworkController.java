package com.artronics.chapar.controller.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.entities.packet.PacketType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;

@Component
public abstract class AbstractNetworkController<T extends Enum<T> & PacketType>
        implements NetworkController<T>{

    private final static Logger log = Logger.getLogger(AbstractNetworkController.class);
    private volatile boolean isStarted = false;

    private BlockingQueue<Packet> packetQueue;

    @Override
    public void start() {
        log.debug("Starting base Network Controller.");
        isStarted = true;
        Thread t = new Thread(new PacketListener(),"PCK-LST");
        t.start();
    }

    @Resource(name = "packetQueue")
    public void setPacketQueue(BlockingQueue<Packet> packetQueue) {
        this.packetQueue = packetQueue;
    }

    private class PacketListener implements Runnable{

        @Override
        public void run() {
            while (isStarted){
                while (!packetQueue.isEmpty()){
                    try {
                        Packet<T> packet = packetQueue.take();
                        processePacket(packet);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
