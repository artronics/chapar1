package com.artronics.chapar.domain.map.graph;

import com.artronics.chapar.domain.model.Node;
import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class JGraphTDelegator implements GraphDelegator
{
    private final Graph<Node, DefaultWeightedEdge> graph;

    public JGraphTDelegator(Graph<Node,DefaultWeightedEdge> graph)
    {
        this.graph = graph;
    }

    @Override
    public List<Node> getShortestPath(Node source, Node target)
    {
        DijkstraShortestPath<Node,DefaultWeightedEdge> dijkstra =
                new DijkstraShortestPath(graph, source, target);

        List<DefaultWeightedEdge> links = dijkstra.getPathEdgeList();

        /*
            Dijkstra returns a list of all links but
            we need a list of sensors. A LinkedHashSet
            preserve the ordering of element and also
            ignore duplicated sensors. At the end we remove
            the source from set. This is because in case
            reversed direction from source to target
            we'll get a wrong order. We'll add source to
            final list.
        */
        Set<Node> sensors = new LinkedHashSet<>();

        for (DefaultWeightedEdge link : links) {
            sensors.add(graph.getEdgeSource(link));
            sensors.add(graph.getEdgeTarget(link));
        }
        sensors.remove(source);

        List<Node> nodesList = new ArrayList<>(sensors);
        nodesList.add(0, source);

        return nodesList;
    }

    @Override
    public Set<Node> getNeighbors(Node sensor)
    {
        if (!graph.containsVertex(sensor))
            throw new IllegalStateException("This sensor is not in map.");

        Set<Node> neighbors = new HashSet<>();
        if (isIsland(sensor))
            return neighbors;

        Set<DefaultWeightedEdge> edges = graph.edgesOf(sensor);

        for (DefaultWeightedEdge edge : edges) {
            Node srcNode = graph.getEdgeSource(edge);
            Node dstNode = graph.getEdgeTarget(edge);

            neighbors.add(srcNode);
            neighbors.add(dstNode);
        }

        //remove sensor from set. we just need its neighbors
        neighbors.remove(sensor);

        return neighbors;
    }

    @Override
    public boolean isIsland(Node sensor)
    {
        return graph.edgesOf(sensor).isEmpty();
    }
}
