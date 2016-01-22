package com.artronics.chapar.core.support.graph;

import com.artronics.chapar.core.entities.Node;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

public class GraphCreator extends NodeDefinitions {
    protected final ListenableUndirectedWeightedGraph<Node, DefaultWeightedEdge> graph1 =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    protected final ListenableUndirectedWeightedGraph<Node, DefaultWeightedEdge> graph2 =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    public GraphCreator() {
        createGraph1();
        createGraph2();
    }

    private void createGraph1() {
        graph1.addVertex(sink1);
        graph1.addVertex(sameAddNode1);
        graph1.addVertex(node135);
        graph1.addVertex(node136);
        graph1.addVertex(node137);
        //Links
        DefaultWeightedEdge e;
        //link sink1 -> 135
        graph1.addEdge(sink1, node135);
        e = graph1.getEdge(sink1, node135);
        graph1.setEdgeWeight(e, 50D);
        //link sink1 -> sameNode1:30
        graph1.addEdge(sink1, sameAddNode1);
        e = graph1.getEdge(sink1, sameAddNode1);
        graph1.setEdgeWeight(e, 10D);
        //link 135 -> sameNode1:30
        graph1.addEdge(node135, sameAddNode1);
        e = graph1.getEdge(node135, sameAddNode1);
        graph1.setEdgeWeight(e, 20D);
        //link 135 -> 136
        graph1.addEdge(node135, node136);
        e = graph1.getEdge(node135, node136);
        graph1.setEdgeWeight(e, 25D);
        //link 137 -> 136
        graph1.addEdge(node137, node136);
        e = graph1.getEdge(node137, node136);
        graph1.setEdgeWeight(e, 30D);
    }

    void createGraph2(){

    }

    /*
     * node number 30 is sameAddNode
     *
     * Graph is like
     *       sink:0
     *       /   \
     *      w50  w10
     *      /      \
     *   135 --w20-- 30
     *     \         /
     *     w25    w100
     *       \    /
     *        136
     *         |
     *        w30
     *         |
     *        137
     *
     *
     */
    public Graph<Node, DefaultWeightedEdge> getSampleGraph1() {
        return graph1;
    }

    /*

     node number 30 is sameAddNode
     Graph is like
                 sink
                     |
                    w10
                     |
                    245
                   /   \
                 w20    w100
                /         \
              246 --w30-- 30
     */

    public Graph<Node, DefaultWeightedEdge> getSampleGraph2() {
        return graph1;
    }


}
