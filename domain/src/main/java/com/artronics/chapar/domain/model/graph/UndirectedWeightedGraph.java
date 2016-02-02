package com.artronics.chapar.domain.model.graph;

public interface UndirectedWeightedGraph <V extends Vertex,E extends Edge>extends Graph<V,E>{
    Double getWeight(V src,V dst);
}
