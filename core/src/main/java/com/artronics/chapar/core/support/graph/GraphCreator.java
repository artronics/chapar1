package com.artronics.chapar.core.support.graph;

import com.artronics.chapar.core.entities.Node;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

public class GraphCreator extends NodeDefinitions {
    protected final ListenableUndirectedWeightedGraph<Node, DefaultWeightedEdge> graph =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    protected final ListenableUndirectedWeightedGraph<Node, DefaultWeightedEdge> graph1 =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    protected final ListenableUndirectedWeightedGraph<Node, DefaultWeightedEdge> graph2 =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    public GraphCreator() {
        createGraph1(graph1);
        createGraph2(graph2);
        createGraph(graph);
    }

    private void createGraph(ListenableUndirectedWeightedGraph<Node,DefaultWeightedEdge> graph) {
        createGraph1(graph);
        createGraph2(graph);
    }

    private void createGraph1(ListenableUndirectedWeightedGraph<Node,DefaultWeightedEdge> graph) {
        graph.addVertex(sink1);
        graph.addVertex(sameAddNode1);
        graph.addVertex(node135);
        graph.addVertex(node136);
        graph.addVertex(node137);
        //Links
        DefaultWeightedEdge e;
        //link sink1 -> 135
        graph.addEdge(sink1, node135);
        e = graph.getEdge(sink1, node135);
        graph.setEdgeWeight(e, 50D);
        //link sink1 -> sameNode1:30
        graph.addEdge(sink1, sameAddNode1);
        e = graph.getEdge(sink1, sameAddNode1);
        graph.setEdgeWeight(e, 10D);
        //link 135 -> sameNode1:30
        graph.addEdge(node135, sameAddNode1);
        e = graph.getEdge(node135, sameAddNode1);
        graph.setEdgeWeight(e, 20D);
        //link 135 -> 136
        graph.addEdge(node135, node136);
        e = graph.getEdge(node135, node136);
        graph.setEdgeWeight(e, 25D);
        //link 136 -> sameNode1:30136
        graph.addEdge(sameAddNode1, node136);
        e = graph.getEdge(sameAddNode1, node136);
        graph.setEdgeWeight(e, 100D);
        //link 137 -> 136
        graph.addEdge(node137, node136);
        e = graph.getEdge(node137, node136);
        graph.setEdgeWeight(e, 30D);
    }

    void createGraph2(ListenableUndirectedWeightedGraph<Node,DefaultWeightedEdge> graph){
        graph.addVertex(sink2);
        graph.addVertex(node245);
        graph.addVertex(node246);
        graph.addVertex(sameAddNode2);

        //Links
        DefaultWeightedEdge e;
        //link sink2 -> 245
        graph.addEdge(sink2, node245);
        e = graph.getEdge(sink2, node245);
        graph.setEdgeWeight(e, 10D);
        //link 245 -> 246
        graph.addEdge(node246, node245);
        e = graph.getEdge(node246, node245);
        graph.setEdgeWeight(e, 20D);
        //link 245 -> 30
        graph.addEdge(sameAddNode2, node245);
        e = graph.getEdge(sameAddNode2, node245);
        graph.setEdgeWeight(e, 100D);
        //link 246 -> 30
        graph.addEdge(sameAddNode2, node246);
        e = graph.getEdge(sameAddNode2, node246);
        graph.setEdgeWeight(e, 30D);
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
    public ListenableUndirectedWeightedGraph<Node, DefaultWeightedEdge> getSampleGraph1() {
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

    public ListenableUndirectedWeightedGraph<Node, DefaultWeightedEdge> getSampleGraph2() {
        return graph1;
    }

    public ListenableUndirectedWeightedGraph<Node,DefaultWeightedEdge> getMixedGraph(){
        return graph;
    }

}
