package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.map.graph.GraphDelegator;
import com.artronics.chapar.core.map.graph.JGraphTDelegator;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeviceMapImpl implements DeviceMap{
    protected ListenableUndirectedWeightedGraph<Node, DefaultWeightedEdge> graph =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    protected GraphDelegator graphDelegator;

    public DeviceMapImpl() {
        graphDelegator = new JGraphTDelegator(graph);
    }

    public DeviceMapImpl(ListenableUndirectedWeightedGraph<Node, DefaultWeightedEdge> graph) {
        this.graph = graph;
        graphDelegator = new JGraphTDelegator(graph);
    }

    @Override
    public void addNode(Node node)
    {
        graph.addVertex(node);
    }

    @Override
    public void removeNode(Node node)
    {
        graph.removeVertex(node);
    }

    @Override
    public void removeLink(Node srcNode, Node target)
    {
        graph.removeEdge(srcNode, target);
    }

    @Override
    public void addLink(Node source, Node target, double weight)
    {
        DefaultWeightedEdge edge = graph.addEdge(source, target);

        if (edge != null) {
            this.graph.setEdgeWeight(edge, weight);
        }
    }

    @Override
    public void addLink(Node source, Node target)
    {
        DefaultWeightedEdge edge = graph.addEdge(source, target);

        if (edge != null) {
            this.graph.setEdgeWeight(edge, DEF_WEIGHT);
        }
    }

    @Override
    public Set<Link> getLinks(Node node) {
        Set<Link> links = new HashSet<>();
        Set<Node> neighbors = getNeighbors(node);
        neighbors.forEach(n->{
            Link link = new Link(n,getWeigh(node,n));
            links.add(link);
        });

        return links;
    }

    @Override
    public boolean hasLink(Node source, Node target)
    {
        return graph.containsEdge(source, target);
    }

    @Override
    public boolean contains(Node node)
    {
        return graph.containsVertex(node);
    }

    @Override
    public boolean isIsland(Node neighbor)
    {
        return graphDelegator.isIsland(neighbor);
    }

    @Override
    public Set<Node> getNeighbors(Node node)
    {
        return graphDelegator.getNeighbors(node);
    }

    @Override
    public Double getWeigh(Node srcNode, Node dstNode) {
        return graphDelegator.getWeigh(srcNode,dstNode);
    }

    @Override
    public List<Node> getAllNodes()
    {
        return new ArrayList<>(graph.vertexSet());
    }

    @Override
    public Graph<Node, DefaultWeightedEdge> getNetworkGraph()
    {
        return this.graph;
    }

    @Override
    public List<Node> getShortestPath(Node source, Node target)
    {
        return graphDelegator.getShortestPath(source,target);
    }
}
