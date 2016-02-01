package com.artronics.chapar.controller.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.entities.packet.PacketType;
import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.controller.services.SensorRegistrationService;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Component
public abstract class AbstractNetworkController<T extends Enum<T> & PacketType>
        implements NetworkController<T>{

    private final static Logger log = Logger.getLogger(AbstractNetworkController.class);
    private volatile boolean isStarted = false;

    private AddressRegistrationService addressRegistrationService;
    private SensorRegistrationService sensorRegistrationService;

    private BlockingQueue<Packet> packetQueue;

    @Override
    public void start() {
        log.debug("Starting base Network Controller.");
        isStarted = true;
        Thread t = new Thread(new PacketListener(),"PCK-LST");
        t.start();
    }

    @Override
    public Packet<T> processPacket(Packet<T> packet) {
        UnicastAddress srcAdd = packet.getSrcAddress();
        Sensor srcSensor = Sensor.create(srcAdd);
        sensorRegistrationService.registerSensor(srcSensor);

        List<UnicastAddress> addresses =
                addressRegistrationService.resolveAddress(packet.getDstAddress());

        addresses.forEach(a->sensorRegistrationService.registerSensor(Sensor.create(a)));

        return packet;
    }

    @Autowired
    public void setAddressRegistrationService(AddressRegistrationService addressRegistrationService) {
        this.addressRegistrationService = addressRegistrationService;
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
                        processPacket(packet);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
