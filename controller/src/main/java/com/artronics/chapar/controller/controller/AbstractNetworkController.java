package com.artronics.chapar.controller.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.entities.packet.PacketType;
import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.controller.services.SensorRegistrationService;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.map.NetworkStructure;
import com.artronics.chapar.domain.repositories.SensorRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Component
public abstract class AbstractNetworkController<T extends Enum<T> & PacketType>
        implements NetworkController<T> {

    private final static Logger log = Logger.getLogger(AbstractNetworkController.class);
    private volatile boolean isStarted = false;

    private AddressRegistrationService addressRegistrationService;
    protected SensorRegistrationService sensorRegistrationService;
    protected NetworkStructure networkStructure;

    protected SensorRepo sensorRepo;

    private BlockingQueue<Packet> packetQueue;

    @Override
    public void start() {
        log.debug("Starting base Network Controller.");
        isStarted = true;
        Thread t = new Thread(new PacketListener(), "PCK-LST");
        t.start();
    }

    @Override
    public Packet<T> processPacket(Packet<T> packet) {
        UnicastAddress srcAdd = packet.getSrcAddress();
        Sensor srcSensor = Sensor.create(srcAdd);
        if (!networkStructure.containsSensor(srcSensor))
            sensorRegistrationService.registerSensor(srcSensor);

        List<UnicastAddress> addresses =
                addressRegistrationService.resolveAddress(packet.getDstAddress());

        addresses.stream()
                .filter(address -> !networkStructure.containsSensor(srcSensor))
                .forEach(address -> sensorRegistrationService.registerSensor(Sensor.create(address)));

        return packet;
    }

    @Autowired
    public void setAddressRegistrationService(AddressRegistrationService addressRegistrationService) {
        this.addressRegistrationService = addressRegistrationService;
    }

    @Autowired
    public void setSensorRegistrationService(SensorRegistrationService sensorRegistrationService) {
        this.sensorRegistrationService = sensorRegistrationService;
    }

    @Autowired
    public void setNetworkStructure(NetworkStructure networkStructure) {
        this.networkStructure = networkStructure;
    }

    @Autowired
    public void setSensorRepo(SensorRepo sensorRepo) {
        this.sensorRepo = sensorRepo;
    }

    @Resource(name = "packetQueue")
    public void setPacketQueue(BlockingQueue<Packet> packetQueue) {
        this.packetQueue = packetQueue;
    }

    private class PacketListener implements Runnable {

        @Override
        public void run() {
            while (isStarted) {
                while (!packetQueue.isEmpty()) {
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
