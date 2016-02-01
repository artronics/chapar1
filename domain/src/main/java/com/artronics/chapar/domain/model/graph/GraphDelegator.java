package com.artronics.chapar.domain.model.graph;

public interface GraphDelegator {
    <V extends Vertex,E extends Edge>UndirectedWeightedGraph<V,E> createUndirectedWeightedGraph(Class<V> vertex,Class<E> edge);
}
