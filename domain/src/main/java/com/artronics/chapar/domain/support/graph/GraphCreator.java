package com.artronics.chapar.domain.support.graph;

import com.artronics.chapar.domain.entities.Sensor;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

public class GraphCreator extends NodeDefinitions {
    protected final ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> graph1 =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    protected final ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> graph2 =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    public GraphCreator() {
        createGraph1();
        createGraph2();
    }

    private void createGraph1() {
        graph1.addVertex(sink1);
        graph1.addVertex(sameAddSensor1);
        graph1.addVertex(sensor135);
        graph1.addVertex(sensor136);
        graph1.addVertex(sensor137);
        //Links
        DefaultWeightedEdge e;
        //link sink1 -> 135
        graph1.addEdge(sink1, sensor135);
        e = graph1.getEdge(sink1, sensor135);
        graph1.setEdgeWeight(e, 50D);
        //link sink1 -> sameNode1:30
        graph1.addEdge(sink1, sameAddSensor1);
        e = graph1.getEdge(sink1, sameAddSensor1);
        graph1.setEdgeWeight(e, 10D);
        //link 135 -> sameNode1:30
        graph1.addEdge(sensor135, sameAddSensor1);
        e = graph1.getEdge(sensor135, sameAddSensor1);
        graph1.setEdgeWeight(e, 20D);
        //link 135 -> 136
        graph1.addEdge(sensor135, sensor136);
        e = graph1.getEdge(sensor135, sensor136);
        graph1.setEdgeWeight(e, 25D);
        //link 137 -> 136
        graph1.addEdge(sensor137, sensor136);
        e = graph1.getEdge(sensor137, sensor136);
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
    public ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> getSampleGraph1() {
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

    public ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> getSampleGraph2() {
        return graph1;
    }


}
