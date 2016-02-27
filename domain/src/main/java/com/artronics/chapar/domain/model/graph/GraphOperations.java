package com.artronics.chapar.domain.model.graph;


import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.exceptions.RouteNotFound;

import java.util.List;
import java.util.Set;

public interface GraphOperations {

    List<Sensor> getShortestPath(Sensor source, Sensor target) throws RouteNotFound;

    Set<Sensor> getNeighbors(Sensor sensor);

    Double getWeigh(Sensor srcSensor, Sensor dstSensor);

    boolean isIsland(Sensor neighbor);
}
