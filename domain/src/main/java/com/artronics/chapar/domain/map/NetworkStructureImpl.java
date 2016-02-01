package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.model.graph.GraphDelegator;
import com.artronics.chapar.domain.model.graph.UndirectedWeightedGraph;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class NetworkStructureImpl implements NetworkStructure{
    private final static Logger log = Logger.getLogger(NetworkStructureImpl.class);
    private final GraphDelegator graphDelegator;
    private final UndirectedWeightedGraph<Sensor,SensorLink> sensorsGraph;

    private Controller controller;

    @Autowired
    public NetworkStructureImpl(GraphDelegator graphDelegator) {
        this.graphDelegator = graphDelegator;
        this.sensorsGraph = graphDelegator.createUndirectedWeightedGraph(Sensor.class,SensorLink.class);
    }

    @Override
    public boolean addController(Controller controller) {
        if (this.controller!=null)
            throw new IllegalStateException("There is already a controller registered. This version of Chapar does not support multiple controllers");

        this.controller = controller;

        return true;
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public boolean addClient(Client client) {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsClient(Client client) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addSensor(Sensor sensor) {
        return sensorsGraph.addVertex(sensor);
    }

    @Override
    public boolean containsSensor(Sensor sensor) {
        return sensorsGraph.containsVertex(sensor);
    }
}
