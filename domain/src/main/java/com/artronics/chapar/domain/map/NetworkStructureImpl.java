package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.model.NetworkComponent;
import com.artronics.chapar.domain.model.graph.GraphDelegator;
import com.artronics.chapar.domain.model.graph.UndirectedWeightedGraph;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class NetworkStructureImpl implements NetworkStructure{
    private final static Logger log = Logger.getLogger(NetworkStructureImpl.class);

    private final GraphDelegator graphDelegator;
    private UndirectedWeightedGraph<Sensor,SensorLink> sensorsGraph;

    private Controller controller;
    private Map<Sensor,Sensor> registeredSensors;

    public NetworkStructureImpl(GraphDelegator graphDelegator, UndirectedWeightedGraph<Sensor, SensorLink> sensorsGraph) {
        this.graphDelegator = graphDelegator;
        this.sensorsGraph = sensorsGraph;
    }

    @Autowired
    public NetworkStructureImpl(GraphDelegator graphDelegator) {
        this.graphDelegator = graphDelegator;
//        this.sensorsGraph = graphDelegator.createUndirectedWeightedGraph(Sensor.class,SensorLink.class);

        this.registeredSensors = new HashMap<>();
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
        registeredSensors.put(sensor,sensor);

        boolean b = sensorsGraph.addVertex(sensor);
        return b;
    }

    @Override
    public Sensor getSensor(Sensor sensor) {

        return registeredSensors.get(sensor);
    }

    @Override
    public Set<Sensor> getNeighbors(Sensor sensor) {
        return null;
    }

    @Override
    public boolean hasLink(Sensor src, Sensor dst) {
        return sensorsGraph.containsEdge(src,dst);
    }

    @Override
    public boolean containsSensor(Sensor sensor) {
        return sensorsGraph.containsVertex(sensor);
    }

    public void setRegisteredSensors(Map<Sensor, Sensor> registeredSensors) {
        this.registeredSensors = registeredSensors;
    }

    @Override
    public boolean isIsolated(NetworkComponent netComponent) {
        if (netComponent instanceof Sensor){
            return sensorsGraph.edgesOf((Sensor) netComponent).isEmpty();
        }

        else {
            throw new NotImplementedException();
        }
    }

    @Override
    public Set<SensorLink> getLinks(Sensor sensor) {
        return null;
    }
}
