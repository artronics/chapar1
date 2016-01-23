package com.artronics.chapar.core.map.graph;

import com.artronics.chapar.core.entities.Node;

import java.util.List;
import java.util.Set;

public interface GraphDelegator
{
    List<Node> getShortestPath(Node source, Node target);

    Set<Node> getNeighbors(Node node);

    Double getWeigh(Node srcNode,Node dstNode);

    boolean isIsland(Node neighbor);
}
