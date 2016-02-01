package com.artronics.chapar.domain.support.graph;

import com.artronics.chapar.domain.entities.Sensor;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

public class GraphCreator extends SensorDefinitions{
    protected final ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> graph =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    protected final ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> graph1 =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    protected final ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> graph2 =
            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

    public GraphCreator() {
        createGraph1(graph1);
        createGraph2(graph2);
        createGraph(graph);
    }

    private void createGraph(ListenableUndirectedWeightedGraph<Sensor,DefaultWeightedEdge> graph) {
        createGraph1(graph);
        createGraph2(graph);
    }

    private void createGraph1(ListenableUndirectedWeightedGraph<Sensor,DefaultWeightedEdge> graph) {
        graph.addVertex(sink1);
        graph.addVertex(sameAddSensor1);
        graph.addVertex(sensor135);
        graph.addVertex(sensor136);
        graph.addVertex(sensor137);
        //Links
        DefaultWeightedEdge e;
        //link sink1 -> 135
        graph.addEdge(sink1, sensor135);
        e = graph.getEdge(sink1, sensor135);
        graph.setEdgeWeight(e, 50D);
        //link sink1 -> sameNode1:30
        graph.addEdge(sink1, sameAddSensor1);
        e = graph.getEdge(sink1, sameAddSensor1);
        graph.setEdgeWeight(e, 10D);
        //link 135 -> sameNode1:30
        graph.addEdge(sensor135, sameAddSensor1);
        e = graph.getEdge(sensor135, sameAddSensor1);
        graph.setEdgeWeight(e, 20D);
        //link 135 -> 136
        graph.addEdge(sensor135, sensor136);
        e = graph.getEdge(sensor135, sensor136);
        graph.setEdgeWeight(e, 25D);
        //link 136 -> sameNode1:30136
        graph.addEdge(sameAddSensor1, sensor136);
        e = graph.getEdge(sameAddSensor1, sensor136);
        graph.setEdgeWeight(e, 100D);
        //link 137 -> 136
        graph.addEdge(sensor137, sensor136);
        e = graph.getEdge(sensor137, sensor136);
        graph.setEdgeWeight(e, 30D);
    }

    void createGraph2(ListenableUndirectedWeightedGraph<Sensor,DefaultWeightedEdge> graph){
        graph.addVertex(sink2);
        graph.addVertex(sensor245);
        graph.addVertex(sensor246);
        graph.addVertex(sameAddSensor2);

        //Links
        DefaultWeightedEdge e;
        //link sink2 -> 245
        graph.addEdge(sink2, sensor245);
        e = graph.getEdge(sink2, sensor245);
        graph.setEdgeWeight(e, 10D);
        //link 245 -> 246
        graph.addEdge(sensor246, sensor245);
        e = graph.getEdge(sensor246, sensor245);
        graph.setEdgeWeight(e, 20D);
        //link 245 -> 30
        graph.addEdge(sameAddSensor2, sensor245);
        e = graph.getEdge(sameAddSensor2, sensor245);
        graph.setEdgeWeight(e, 100D);
        //link 246 -> 30
        graph.addEdge(sameAddSensor2, sensor246);
        e = graph.getEdge(sameAddSensor2, sensor246);
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

    public ListenableUndirectedWeightedGraph<Sensor,DefaultWeightedEdge> getMixedGraph(){
        return graph;
    }
}
