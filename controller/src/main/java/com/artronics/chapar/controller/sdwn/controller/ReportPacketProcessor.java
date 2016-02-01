package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketUtils;
import com.artronics.chapar.controller.services.SensorRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.map.NetworkStructure;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ReportPacketProcessor {
    private final static Logger log = Logger.getLogger(ReportPacketProcessor.class);

    public final static int NEIGHBOR_INDEX = 13;

    private WeightCalculator weightCalculator;

    private SensorRegistrationService sensorRegistrationService;
    private NetworkStructure networkStructure;

    public ReportPacketProcessor() {
        weightCalculator = new RssiSimpleWeightCalculator();
    }

    Packet<SdwnPacketType> processReportPacket(Packet<SdwnPacketType> packet){
        assert packet.getType()==SdwnPacketType.REPORT;

        return packet;
    }

    public Set<SensorLink> createSensorLinks(Packet packet){
        List<Integer> content = packet.getBuffer().getContent();
        Client client = packet.getSrcAddress().getClient();

        Set<SensorLink> links = new HashSet<>();

        for (int i = NEIGHBOR_INDEX; i < content.size(); i += 3) {
            Integer add = SdwnPacketUtils.joinAddresses(content.get(i),
                    content.get(i + 1));
            int rssi = content.get(i + 2);

            Double weight = weightCalculator.calculate(rssi);

            Sensor sensor = Sensor.create(UnicastAddress.create(client,add.longValue()));
            SensorLink neighbor = new SensorLink(sensor,weight);
            links.add(neighbor);
        }

        return links;
    }

    public void setWeightCalculator(WeightCalculator weightCalculator) {
        this.weightCalculator = weightCalculator;
    }

    public void setSensorRegistrationService(SensorRegistrationService sensorRegistrationService) {
        this.sensorRegistrationService = sensorRegistrationService;
    }

    public void setNetworkStructure(NetworkStructure networkStructure) {
        this.networkStructure = networkStructure;
    }
}
