package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.SensorRegistrationService;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.map.NetworkStructure;
import com.artronics.chapar.domain.repositories.SensorRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorRegistrationServiceImpl implements SensorRegistrationService{
    private final static Logger log = Logger.getLogger(SensorRegistrationServiceImpl.class);

    private NetworkStructure networkStructure;
    private SensorRepo sensorRepo;

    @Autowired
    public void setNetworkStructure(NetworkStructure networkStructure) {
        this.networkStructure = networkStructure;
    }

    @Override
    public Sensor registerSensor(Sensor sensor) {
        networkStructure.addSensor(sensor);

        sensorRepo.save(sensor);

        return sensor;
    }

    @Autowired
    public void setSensorRepo(SensorRepo sensorRepo) {
        this.sensorRepo = sensorRepo;
    }

}
