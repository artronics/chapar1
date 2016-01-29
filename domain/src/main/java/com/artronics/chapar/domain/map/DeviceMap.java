package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.map.graph.GraphDelegator;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;

public interface DeviceMap extends GraphDelegator
{
    void addNode(Sensor sensor);

    void removeNode(Sensor sensor);

    void addLink(Sensor source, Sensor target, double weight);

    void removeLink(Sensor srcSensor, Sensor neighbor);

    boolean hasLink(Sensor source, Sensor target);

    boolean contains(Sensor sensor);

    List<Sensor> getAllNodes();

    Graph<Sensor, DefaultWeightedEdge> getNetworkGraph();
}
