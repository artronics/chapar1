package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import org.apache.log4j.Logger;

import java.util.Set;

public class DeviceMapUpdaterImpl implements DeviceMapUpdater{
    private final static Logger log = Logger.getLogger(DeviceMapUpdaterImpl.class);

    @Override
    public void update(DeviceMap deviceMap, Sensor srcSensor, Set<Sensor> newNeighbors) {

    }
}
