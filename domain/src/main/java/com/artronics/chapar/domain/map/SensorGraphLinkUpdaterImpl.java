package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import org.apache.log4j.Logger;

import java.util.Set;

public class SensorGraphLinkUpdaterImpl implements SensorGraphLinkUpdater{
    private final static Logger log = Logger.getLogger(SensorGraphLinkUpdaterImpl.class);

    @Override
    public void update(Sensor srcSensor, Set<SensorLink> links, Set<Sensor> islandSensors) {

    }

}
