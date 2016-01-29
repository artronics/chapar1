package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.map.graph.GraphDelegator;
import com.artronics.chapar.domain.model.Node;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;

public interface NetworkMap extends GraphDelegator
{
    void addNode(Node node);

    void removeNode(Node node);

    void addLink(Node source, Node target, double weight);

    void removeLink(Node srcNode, Node neighbor);

    boolean hasLink(Node source, Node target);

    boolean contains(Node sensor);

    List<Node> getAllNodes();

    Graph<Node, DefaultWeightedEdge> getNetworkGraph();
}
