package com.artronics.chapar.domain.model.graph;


import com.artronics.chapar.domain.entities.Sensor;

public interface GraphDelegator extends GraphOperations
{
    void addLink(Sensor source, Sensor target, double weight);
}
