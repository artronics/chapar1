package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.map.graph.GraphOperations;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;
import java.util.Set;

public interface NodeMap extends GraphOperations
{
    Double DEF_WEIGHT = 1.0D;

    void addNode(Node node);

    void removeNode(Node node);

    void addLink(Node source, Node target, double weight);

    void addLink(Node source, Node target);

    Set<Link> getLinks(Node node);

    void removeLink(Node srcNode, Node neighbor);

    boolean hasLink(Node source, Node target);

    boolean contains(Node node);

    List<Node> getAllNodes();

    Graph<Node, DefaultWeightedEdge> getNetworkGraph();
}
