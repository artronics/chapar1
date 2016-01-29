package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;

import java.util.Set;

public interface DeviceMapUpdater {
    void update(DeviceMap deviceMap, Sensor srcSensor, Set<Sensor> newNeighbors);
}
