package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.model.NetworkComponent;

import java.util.Set;

public interface NetworkStructure {
    boolean addClient(Client client);

    boolean containsClient(Client client);

    boolean addSensor(Sensor sensor);

    Sensor getSensor(Sensor sensor);

    Set<Sensor> getNeighbors(Sensor sensor);

    boolean hasLink(Sensor src,Sensor dst);

    boolean containsSensor(Sensor sensor);

    boolean addController(Controller controller);

    Controller getController();

    boolean isIsolated(NetworkComponent netComponent);

    Set<SensorLink> getLinks(Sensor sensor);

}
