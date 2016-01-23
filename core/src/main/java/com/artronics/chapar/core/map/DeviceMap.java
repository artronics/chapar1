package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.map.graph.GraphDelegator;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;

public interface DeviceMap extends GraphDelegator
{
    Double DEF_WEIGHT = 1.0D;

    void addNode(Node node);

    void removeNode(Node node);

    void addLink(Node source, Node target, double weight);

    void addLink(Node source, Node target);

    void removeLink(Node srcNode, Node neighbor);

    boolean hasLink(Node source, Node target);

    boolean contains(Node node);

    List<Node> getAllNodes();

    Graph<Node, DefaultWeightedEdge> getNetworkGraph();
}
