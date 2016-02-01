package com.artronics.chapar.domain.map.graph;

import com.artronics.chapar.domain.model.Vertex;
import org.apache.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Component;

@Component
public class JGraphTDelegator implements GraphDelegator{
    private final static Logger log = Logger.getLogger(JGraphTDelegator.class);

    private final Graph<Vertex, DefaultWeightedEdge> graph;

    public JGraphTDelegator(Graph<Vertex, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    @Override
    public boolean addVertex(Vertex vertex) {
        return graph.addVertex(vertex);
    }
}
