package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.exceptions.RouteNotFound;
import com.artronics.chapar.domain.model.graph.GraphDelegator;
import com.artronics.chapar.domain.model.graph.impl.JGraphTDelegator;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class NodeMapImpl implements NodeMap {
    protected ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> graph =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    protected GraphDelegator graphDelegator;

    public NodeMapImpl() {
        graphDelegator = new JGraphTDelegator(graph);
    }

    public NodeMapImpl(ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> graph) {
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
        //When the graph already has a link it returns null
        DefaultWeightedEdge edge = graph.addEdge(source, target);

        if (edge != null) {
            this.graph.setEdgeWeight(edge, weight);
        }

        //if it returns null any way we need to update the link
        else {
            removeLink(source,target);
            addLink(source,target,weight);
        }
    }

    @Override
    public void addLink(Sensor source, Sensor target)
    {
        addLink(source,target,DEF_WEIGHT);
    }

    @Override
    public Set<SensorLink> getLinks(Sensor sensor) {
        Set<SensorLink> links = new HashSet<>();
        Set<Sensor> neighbors = getNeighbors(sensor);
        neighbors.forEach(n->{
            SensorLink link = new SensorLink(n,getWeigh(sensor,n));
            links.add(link);
        });

        return links;
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
    public Double getWeigh(Sensor srcSensor, Sensor dstSensor) {
        return graphDelegator.getWeigh(srcSensor, dstSensor);
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
    public List<Sensor> getShortestPath(Sensor source, Sensor target) throws RouteNotFound {
        return graphDelegator.getShortestPath(source,target);
    }

}
