package com.artronics.chapar.domain.model.graph.impl;

import com.artronics.chapar.domain.model.graph.Edge;
import org.apache.log4j.Logger;
import org.jgrapht.graph.DefaultWeightedEdge;

public class JgraphTEdge extends DefaultWeightedEdge implements Edge{
    private final static Logger log = Logger.getLogger(JgraphTEdge.class);
}
