package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketUtils;
import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.controller.services.SensorRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.map.NetworkStructure;
import com.artronics.chapar.domain.repositories.SensorRepo;
import com.artronics.chapar.domain.support.NodeMapPrinter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
class ReportPacketProcessor {
    private final static Logger log = Logger.getLogger(ReportPacketProcessor.class);
    private final static Logger mapLog = Logger.getLogger("com.artronics.chapar.logger.map");
    private final static NodeMapPrinter mapPrinter = new NodeMapPrinter();

    public final static int NEIGHBOR_INDEX = 13;

    private WeightCalculator weightCalculator;

    private SensorRegistrationService sensorRegistrationService;
    private AddressRegistrationService addressRegistrationService;
    private NetworkStructure networkStructure;

    private NodeMapPrinter nodeMapPrinter = new NodeMapPrinter();

    private SensorRepo sensorRepo;

    private Set<Sensor> isolatedSensors= new HashSet<>();

    Packet<SdwnPacketType> processReportPacket(Packet<SdwnPacketType> packet) {
        assert packet.getType() == SdwnPacketType.REPORT;

        Set<SensorLink> links = createSensorLinks(packet);

        List<SensorLink> regLinks=registerNeighborsIfNecessary(links);

        Sensor src = updateSrc(packet,regLinks);

        mapLog.debug(mapPrinter.printDeviceMap(networkStructure.getNodeMap(),packet.getBuffer().getClient()));

        isolatedSensors.clear();
        networkStructure.updateMap(src,new HashSet<>(regLinks),isolatedSensors);

        removeIsolatedSensors(isolatedSensors);


        return packet;
    }

    private Sensor updateSrc(Packet<SdwnPacketType> packet,List<SensorLink> regLinks) {
        //When we get a report packet it means that the sensor inside db must be update
        //with new instance. This sensor corresponds to source address of packet
        UnicastAddress ua = packet.getSrcAddress();
        Sensor src = Sensor.create(ua);

        src.setBattery((double) SdwnPacketUtils.getBattery(packet.getBuffer().getContent()));
        src.setLinks(regLinks);

        sensorRepo.save(src);

        return src;
    }

    private List<SensorLink> registerNeighborsIfNecessary(Set<SensorLink> links) {
        for (SensorLink link : links) {
            Sensor dstSensor = link.getDstSensor();

            //First register associated address
            dstSensor.setAddress(
                    addressRegistrationService.registerUnicastAddress(
                            dstSensor.getAddress().getLocalAddress(),dstSensor.getAddress().getClient())
            );

            //Second register associated dstSensor with registered address(from previous step)
            link.setDstSensor(
                        sensorRegistrationService.registerSensor(dstSensor)
                );
        }

        return new ArrayList<>(links);
    }

    public Set<SensorLink> createSensorLinks(Packet packet) {
        List<Integer> content = packet.getBuffer().getContent();
        Client client = packet.getSrcAddress().getClient();

        Set<SensorLink> links = new HashSet<>();

        for (int i = NEIGHBOR_INDEX; i < content.size(); i += 3) {
            Integer add = SdwnPacketUtils.joinAddresses(content.get(i),
                    content.get(i + 1));
            int rssi = content.get(i + 2);

            Double weight = weightCalculator.calculate(rssi);

            Sensor sensor = Sensor.create(UnicastAddress.create(client, add.longValue()));
            SensorLink neighbor = new SensorLink(sensor, weight);
            links.add(neighbor);
        }

        return links;
    }

    private void removeIsolatedSensors(Set<Sensor> isolatedSensors){
        if (!isolatedSensors.isEmpty()) {
            isolatedSensors.forEach(sensor -> {
                log.debug("Find island " + sensor);
                sensorRegistrationService.unregisterSensor(sensor);

                mapLog.debug(nodeMapPrinter.printDeviceMap(
                        networkStructure.getNodeMap(),sensor.getAddress().getClient()));
            });

        }
    }

    @Autowired
    public void setWeightCalculator(WeightCalculator weightCalculator) {
        this.weightCalculator = weightCalculator;
    }

    @Autowired
    public void setSensorRegistrationService(SensorRegistrationService sensorRegistrationService) {
        this.sensorRegistrationService = sensorRegistrationService;
    }

    @Autowired
    public void setAddressRegistrationService(AddressRegistrationService addressRegistrationService) {
        this.addressRegistrationService = addressRegistrationService;
    }

    @Autowired
    public void setNetworkStructure(NetworkStructure networkStructure) {
        this.networkStructure = networkStructure;
    }

    @Autowired
    public void setSensorRepo(SensorRepo sensorRepo) {
        this.sensorRepo = sensorRepo;
    }

    public void setNodeMapPrinter(NodeMapPrinter nodeMapPrinter) {
        this.nodeMapPrinter = nodeMapPrinter;
    }

}
