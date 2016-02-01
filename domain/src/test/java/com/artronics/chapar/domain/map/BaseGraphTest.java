package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.model.graph.GraphDelegator;
import com.artronics.chapar.domain.model.graph.impl.JGraphTDelegator;
import com.artronics.chapar.domain.support.graph.GraphCreator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.junit.Before;

public class BaseGraphTest {
    protected GraphCreator graphCreator = new GraphCreator();

    protected GraphDelegator graphDelegator;

    protected ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> sampleGraph1;
    protected ListenableUndirectedWeightedGraph<Sensor, DefaultWeightedEdge> multipleDeviceGraph;

    protected Client client;

    protected Sensor sink1;
    protected Sensor sensor30;
    protected Sensor sensor135;
    protected Sensor sensor136;
    protected Sensor sensor137;

    protected Sensor sameAddSensor1;
    protected Sensor sameAddSensor2;

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
    @Before
    public void setUp() throws Exception {
        sampleGraph1 = graphCreator.getSampleGraph1();
        multipleDeviceGraph = graphCreator.getMixedGraph();
        graphDelegator = new JGraphTDelegator(sampleGraph1);

        client = graphCreator.getClient();

        sink1 = graphCreator.getSink1();
        sensor30 = graphCreator.getSameAddSensor1();
        sensor135 = graphCreator.getSensor135();
        sensor136 = graphCreator.getSensor136();
        sensor137 = graphCreator.getSensor137();

        sameAddSensor1 = graphCreator.getSameAddSensor1();
        sameAddSensor2 = graphCreator.getSameAddSensor2();
    }

}
