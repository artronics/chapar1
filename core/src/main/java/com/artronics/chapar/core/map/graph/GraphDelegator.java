package com.artronics.chapar.core.map.graph;

import com.artronics.chapar.core.entities.Node;

public interface GraphDelegator extends GraphOperations
{
    void addLink(Node source, Node target, double weight);
}
