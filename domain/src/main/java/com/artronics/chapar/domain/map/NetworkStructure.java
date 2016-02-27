package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;

import java.util.Set;

/**
 * The interface Network structure.
 */
public interface NetworkStructure {

    /**
     * Use this method when a {@link Client} asks for registration.
     *
     * @param client the client
     * @return the boolean
     */
    boolean addClient(Client client);

    boolean containsClient(Client client);

    void addSensor(Sensor sensor);

    Sensor getSensor(Sensor sensor);

    Set<Sensor> getNeighbors(Sensor sensor);

    boolean hasLink(Sensor src,Sensor dst);

    boolean containsSensor(Sensor sensor);

    boolean addController(Controller controller);

    Controller getController();

    boolean isIsolated(Sensor sensor);

    Set<SensorLink> getLinks(Sensor sensor);

    void updateMap(Sensor srcSensor, Set<SensorLink> links, Set<Sensor> isolatedSensors);
}
