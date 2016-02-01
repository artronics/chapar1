package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.Sensor;

public interface NetworkStructure {
    boolean addClient(Client client);

    boolean containsClient(Client client);

    boolean addSensor(Sensor sensor);

    boolean containsSensor(Sensor sensor);

    boolean addController(Controller controller);
}
