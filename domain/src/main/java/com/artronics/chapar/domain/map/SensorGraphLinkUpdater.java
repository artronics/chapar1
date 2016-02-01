package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;

import java.util.Set;

public interface SensorGraphLinkUpdater {

    void update(Sensor srcSensor, Set<SensorLink> links, Set<Sensor> islandSensors);

}
