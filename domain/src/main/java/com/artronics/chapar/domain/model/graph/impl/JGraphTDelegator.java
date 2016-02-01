package com.artronics.chapar.domain.model.graph.impl;

import com.artronics.chapar.domain.model.graph.Edge;
import com.artronics.chapar.domain.model.graph.GraphDelegator;
import com.artronics.chapar.domain.model.graph.UndirectedWeightedGraph;
import com.artronics.chapar.domain.model.graph.Vertex;
import org.apache.log4j.Logger;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.springframework.stereotype.Component;

@Component
public class JGraphTDelegator implements GraphDelegator {
    private final static Logger log = Logger.getLogger(JGraphTDelegator.class);

    private final ListenableUndirectedWeightedGraph<? extends Vertex, DefaultWeightedEdge> graph;

    public JGraphTDelegator(ListenableUndirectedWeightedGraph<? extends Vertex, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    public JGraphTDelegator() {
        graph = new ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    }



    @Override
    public <V extends Vertex, E extends Edge> UndirectedWeightedGraph<V, E> createUndirectedWeightedGraph(Class<V> vertex, Class<E> edge) {
        ListenableUndirectedWeightedGraph<V,E> jgraph = new ListenableUndirectedWeightedGraph(DefaultWeightedEdge.class);
        UndirectedWeightedGraph<V,E> graphWrapper = new JGraphTUndirectedWeightedGraphWrapper<>(jgraph);

        return graphWrapper;
    }

}
