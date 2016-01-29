package com.artronics.chapar.domain.map.graph;

import com.artronics.chapar.domain.model.Node;

import java.util.List;
import java.util.Set;

public interface GraphDelegator
{
    List<Node> getShortestPath(Node source, Node target);

    Set<Node> getNeighbors(Node sensor);

    boolean isIsland(Node neighbor);
}
