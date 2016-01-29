package com.artronics.chapar.domain.map.graph;

import com.artronics.chapar.domain.entities.Sensor;

import java.util.List;
import java.util.Set;

public interface GraphDelegator
{
    List<Sensor> getShortestPath(Sensor source, Sensor target);

    Set<Sensor> getNeighbors(Sensor sensor);

    boolean isIsland(Sensor neighbor);
}
