package com.artronics.chapar.core.map.graph;

import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.RouteNotFound;

import java.util.List;
import java.util.Set;

public interface GraphOperations {

    List<Node> getShortestPath(Node source, Node target) throws RouteNotFound;

    Set<Node> getNeighbors(Node node);

    Double getWeigh(Node srcNode,Node dstNode);

    boolean isIsland(Node neighbor);
}
