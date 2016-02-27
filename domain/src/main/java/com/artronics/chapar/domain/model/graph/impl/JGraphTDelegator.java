package com.artronics.chapar.domain.model.graph.impl;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.exceptions.RouteNotFound;
import com.artronics.chapar.domain.model.graph.GraphDelegator;
import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class JGraphTDelegator implements GraphDelegator
{
    private final Graph<Sensor, DefaultWeightedEdge> graph;

    public JGraphTDelegator(Graph<Sensor,DefaultWeightedEdge> graph)
    {
        this.graph = graph;
    }

    @Override
    public void addLink(Sensor source, Sensor target, double weight) {

    }

    @Override
    public List<Sensor> getShortestPath(Sensor source, Sensor target) throws RouteNotFound {
        DijkstraShortestPath<Sensor,DefaultWeightedEdge> dijkstra =
                new DijkstraShortestPath(graph, source, target);

        List<DefaultWeightedEdge> links = dijkstra.getPathEdgeList();

        if (links==null)
            throw new RouteNotFound();
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
        Set<Sensor> sensors = new LinkedHashSet<>();

        for (DefaultWeightedEdge link : links) {
            sensors.add(graph.getEdgeSource(link));
            sensors.add(graph.getEdgeTarget(link));
        }
        sensors.remove(source);

        List<Sensor> nodesList = new ArrayList<>(sensors);
        nodesList.add(0, source);

        return nodesList;
    }

    @Override
    public Set<Sensor> getNeighbors(Sensor sensor)
    {
        if (!graph.containsVertex(sensor))
            throw new IllegalStateException("This sensor is not in map.");

        Set<Sensor> neighbors = new HashSet<>();
        if (isIsland(sensor))
            return neighbors;

        Set<DefaultWeightedEdge> edges = graph.edgesOf(sensor);

        for (DefaultWeightedEdge edge : edges) {
            Sensor srcSensor = graph.getEdgeSource(edge);
            Sensor dstSensor = graph.getEdgeTarget(edge);

            neighbors.add(srcSensor);
            neighbors.add(dstSensor);
        }

        //remove sensor from set. we just need its neighbors
        neighbors.remove(sensor);

        return neighbors;
    }

    @Override
    public Double getWeigh(Sensor srcSensor, Sensor dstSensor) {
        DefaultWeightedEdge e = graph.getEdge(srcSensor, dstSensor);
        return graph.getEdgeWeight(e);
    }

    @Override
    public boolean isIsland(Sensor sensor)
    {
        return graph.edgesOf(sensor).isEmpty();
    }
}
