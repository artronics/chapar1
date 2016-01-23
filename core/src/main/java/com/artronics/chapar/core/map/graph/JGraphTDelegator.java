package com.artronics.chapar.core.map.graph;

import com.artronics.chapar.core.entities.Node;
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
    public void addLink(Node source, Node target, double weight) {

    }

    @Override
    public List<Node> getShortestPath(Node source, Node target)
    {
        DijkstraShortestPath<Node,DefaultWeightedEdge> dijkstra =
                new DijkstraShortestPath(graph, source, target);

        List<DefaultWeightedEdge> links = dijkstra.getPathEdgeList();

        /*
            Dijkstra returns a list of all links but
            we need a list of nodes. A LinkedHashSet
            preserve the ordering of element and also
            ignore duplicated nodes. At the end we remove
            the source from set. This is because in case
            reversed direction from source to target
            we'll get a wrong order. We'll add source to
            final list.
        */
        Set<Node> nodes = new LinkedHashSet<>();

        for (DefaultWeightedEdge link : links) {
            nodes.add(graph.getEdgeSource(link));
            nodes.add(graph.getEdgeTarget(link));
        }
        nodes.remove(source);

        List<Node> nodesList = new ArrayList<>(nodes);
        nodesList.add(0, source);

        return nodesList;
    }

    @Override
    public Set<Node> getNeighbors(Node node)
    {
        if (!graph.containsVertex(node))
            throw new IllegalStateException("This node is not in map.");

        Set<Node> neighbors = new HashSet<>();
        if (isIsland(node))
            return neighbors;

        Set<DefaultWeightedEdge> edges = graph.edgesOf(node);

        for (DefaultWeightedEdge edge : edges) {
            Node srcNode = graph.getEdgeSource(edge);
            Node dstNode = graph.getEdgeTarget(edge);

            neighbors.add(srcNode);
            neighbors.add(dstNode);
        }

        //remove node from set. we just need its neighbors
        neighbors.remove(node);

        return neighbors;
    }

    @Override
    public Double getWeigh(Node srcNode, Node dstNode) {
        DefaultWeightedEdge e = graph.getEdge(srcNode,dstNode);
        return graph.getEdgeWeight(e);
    }

    @Override
    public boolean isIsland(Node node)
    {
        return graph.edgesOf(node).isEmpty();
    }
}
