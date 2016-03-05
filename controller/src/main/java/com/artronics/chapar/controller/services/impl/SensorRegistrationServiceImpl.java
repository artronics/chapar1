package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.SensorRegistrationService;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.map.NetworkStructure;
import com.artronics.chapar.domain.repositories.SensorRepo;
import com.artronics.chapar.domain.support.NodeMapPrinter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorRegistrationServiceImpl implements SensorRegistrationService {
    private final static Logger log = Logger.getLogger(SensorRegistrationServiceImpl.class);
    private final static Logger mapLog = Logger.getLogger("com.artronics.chapar.logger.map");

    private NetworkStructure networkStructure;
    private SensorRepo sensorRepo;

    private NodeMapPrinter nodeMapPrinter = new NodeMapPrinter();

    @Autowired
    public void setNetworkStructure(NetworkStructure networkStructure) {
        this.networkStructure = networkStructure;
    }

    @Override
    public Sensor registerSensor(Sensor sensor) {
        if (networkStructure.containsSensor(sensor))
            return networkStructure.getSensor(sensor);

        networkStructure.addSensor(sensor);

        sensorRepo.save(sensor);
        log.debug("Register new Sensor: " + sensor);

        mapLog.debug(nodeMapPrinter.printDeviceMap(
                networkStructure.getNodeMap(),sensor.getAddress().getClient()));

        return sensor;
    }

    @Override
    public void unregisterSensor(Sensor sensor) {
        if (networkStructure.containsSensor(sensor))
            networkStructure.removeSensor(sensor);
    }

    @Autowired
    public void setSensorRepo(SensorRepo sensorRepo) {
        this.sensorRepo = sensorRepo;
    }

}
