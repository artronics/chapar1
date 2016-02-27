package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.exceptions.SensorNotRegistered;

import java.util.Set;

public interface NodeMapUpdater {
    void update(Sensor srcSensor, Set<SensorLink> links, Set<Sensor> islandSensors) throws SensorNotRegistered;

    void update(NodeMap nodeMap, Sensor srcSensor, Set<SensorLink> links, Set<Sensor> islandSensors) throws SensorNotRegistered;

    void update(NodeMap nodeMap, Sensor srcSensor, Set<SensorLink> links) throws SensorNotRegistered;
}
