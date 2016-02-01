package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.exceptions.SensorNotRegistered;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class SensorGraphLinkUpdaterImpl implements SensorGraphLinkUpdater{
    private final static Logger log = Logger.getLogger(SensorGraphLinkUpdaterImpl.class);
    private NetworkStructure networkStructure;

    @Override
    public void update(Sensor srcSensor, Set<SensorLink> links, Set<Sensor> islandSensors) {
        checkRegistrations(srcSensor,links);

    }

    @Override
    public void update(Sensor srcSensor, Set<SensorLink> links) {
        update(srcSensor,links,null);
    }

    private void checkRegistrations(Sensor srcSen, Set<SensorLink> links) {
        if (!networkStructure.containsSensor(srcSen)) {
            throw new SensorNotRegistered();
        }

        if(links.stream().anyMatch(l->!networkStructure.containsSensor(l.getDstSensor()))){
            throw new SensorNotRegistered();
        }
    }

    @Autowired
    public void setNetworkStructure(NetworkStructure networkStructure) {
        this.networkStructure = networkStructure;
    }

}
