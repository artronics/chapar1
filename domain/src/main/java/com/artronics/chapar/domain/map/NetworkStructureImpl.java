package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
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

    private NodeMap nodeMap;

    private Controller controller;
    private Map<Sensor,Sensor> registeredSensors;
    private NodeMapUpdater nodeMapUpdater;

    public NetworkStructureImpl() {
        registeredSensors = new HashMap<>();
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
    public void addSensor(Sensor sensor) {
        registeredSensors.put(sensor,sensor);

        nodeMap.addNode(sensor);
    }

    @Override
    public void removeSensor(Sensor sensor) {
        registeredSensors.remove(sensor);
        nodeMap.removeNode(sensor);
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
        return nodeMap.hasLink(src,dst);
    }

    @Override
    public boolean containsSensor(Sensor sensor) {
        return nodeMap.contains(sensor);
    }

    @Override
    public void updateMap(Sensor srcSensor, Set<SensorLink> links, Set<Sensor> isolatedSensors) {
        nodeMapUpdater.update(srcSensor,links,isolatedSensors);
    }

    public void setRegisteredSensors(Map<Sensor, Sensor> registeredSensors) {
        this.registeredSensors = registeredSensors;
    }

    @Override
    public boolean isIsolated(Sensor sensor) {
        return nodeMap.isIsland(sensor);
    }

    @Override
    public NodeMap getNodeMap() {
        return nodeMap;
    }

    @Override
    public Set<SensorLink> getLinks(Sensor sensor) {
        return nodeMap.getLinks(sensor);
    }

    @Autowired
    public void setNodeMap(NodeMap nodeMap) {
        this.nodeMap = nodeMap;
    }

    @Autowired
    public void setNodeMapUpdater(NodeMapUpdater nodeMapUpdater) {
        this.nodeMapUpdater = nodeMapUpdater;
    }

}
