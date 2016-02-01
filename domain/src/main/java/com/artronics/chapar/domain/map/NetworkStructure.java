package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;

public interface NetworkStructure {
    boolean addClient(Client client);

    boolean containsClient(Client client);

    boolean addSensor()
}
