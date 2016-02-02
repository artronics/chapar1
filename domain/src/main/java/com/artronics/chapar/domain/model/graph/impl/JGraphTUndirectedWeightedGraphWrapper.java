package com.artronics.chapar.domain.model.graph.impl;

import com.artronics.chapar.domain.model.graph.Edge;
import com.artronics.chapar.domain.model.graph.UndirectedWeightedGraph;
import com.artronics.chapar.domain.model.graph.Vertex;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

public class JGraphTUndirectedWeightedGraphWrapper<V extends Vertex,E extends Edge>
        implements UndirectedWeightedGraph<V,E>{

    private final ListenableUndirectedWeightedGraph<V, E> graph;

    public JGraphTUndirectedWeightedGraphWrapper(ListenableUndirectedWeightedGraph<V, E> graph) {
        this.graph = graph;
    }

    @Override
    public boolean addVertex(V vertex) {
        return graph.addVertex(vertex);
    }

    @Override
    public boolean containsVertex(V vertex) {
        return graph.containsVertex(vertex);
    }

    public ListenableUndirectedWeightedGraph<V, E> getGraph() {
        return graph;
    }

    @Override
    public Set<E> edgesOf(V vertex) {
        return graph.edgesOf(vertex);
    }

    @Override
    public boolean containsEdge(V sourceVertex, V targetVertex) {
        return graph.containsEdge(sourceVertex,targetVertex);
    }

    @Override
    public Double getWeight(V src, V dst) {
//        DefaultWeightedEdge e =graph.getEdge(src,dst);
//        graph.getEdgeWeight()
        throw new NotImplementedException();
    }
}
