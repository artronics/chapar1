package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.map.graph.GraphDelegator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NetworkStructureImpl implements NetworkStructure{
    private final static Logger log = Logger.getLogger(NetworkStructureImpl.class);
    private final GraphDelegator graphDelegator;

    @Autowired
    public NetworkStructureImpl(GraphDelegator graphDelegator) {
        this.graphDelegator = graphDelegator;
    }

    @Override
    public boolean addClient(Client client) {
        return graphDelegator.addVertex(client);
    }

    @Override
    public boolean containsClient(Client client) {
        return graphDelegator.containsVertex(client);
    }

    @Override
    public boolean addSensor(Sensor sensor) {
        return graphDelegator.addVertex(sensor);
    }

    @Override
    public boolean containsSensor(Sensor sensor) {
        return graphDelegator.containsVertex(sensor);
    }
}
