package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.model.graph.GraphOperations;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;
import java.util.Set;

public interface NodeMap extends GraphOperations
{
    Double DEF_WEIGHT = 1.0D;

    void addNode(Sensor sensor);

    void removeNode(Sensor sensor);

    void addLink(Sensor source, Sensor target, double weight);

    void addLink(Sensor source, Sensor target);

    Set<SensorLink> getLinks(Sensor sensor);

    void removeLink(Sensor srcSensor, Sensor neighbor);

    boolean hasLink(Sensor source, Sensor target);

    boolean contains(Sensor sensor);

    List<Sensor> getAllNodes();

    Graph<Sensor, DefaultWeightedEdge> getNetworkGraph();
}
