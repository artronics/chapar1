package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.map.graph.GraphDelegator;
import com.artronics.chapar.domain.map.graph.JGraphTDelegator;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeviceMapImpl implements DeviceMap{
    protected ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> graph =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    protected GraphDelegator graphDelegator;

    public DeviceMapImpl() {
        graphDelegator = new JGraphTDelegator(graph);
    }

    public DeviceMapImpl(ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> graph) {
        this.graph = graph;
        graphDelegator = new JGraphTDelegator(graph);
    }

    @Override
    public void addNode(Sensor sensor)
    {
        graph.addVertex(sensor);
    }

    @Override
    public void removeNode(Sensor sensor)
    {
        graph.removeVertex(sensor);
    }

    @Override
    public void removeLink(Sensor srcSensor, Sensor target)
    {
        graph.removeEdge(srcSensor, target);
    }

    @Override
    public void addLink(Sensor source, Sensor target, double weight)
    {
        DefaultWeightedEdge edge = graph.addEdge(source, target);

        if (edge != null) {
            this.graph.setEdgeWeight(edge, weight);
        }
    }

    @Override
    public boolean hasLink(Sensor source, Sensor target)
    {
        return graph.containsEdge(source, target);
    }

    @Override
    public boolean contains(Sensor sensor)
    {
        return graph.containsVertex(sensor);
    }

    @Override
    public boolean isIsland(Sensor neighbor)
    {
        return graphDelegator.isIsland(neighbor);
    }

    @Override
    public Set<Sensor> getNeighbors(Sensor sensor)
    {
        return graphDelegator.getNeighbors(sensor);
    }

    @Override
    public List<Sensor> getAllNodes()
    {
        return new ArrayList<>(graph.vertexSet());
    }

    @Override
    public Graph<Sensor, DefaultWeightedEdge> getNetworkGraph()
    {
        return this.graph;
    }

    @Override
    public List<Sensor> getShortestPath(Sensor source, Sensor target)
    {
        return graphDelegator.getShortestPath(source,target);
    }
}
