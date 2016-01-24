package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.map.graph.GraphDelegator;
import com.artronics.chapar.core.map.graph.JGraphTDelegator;
import com.artronics.chapar.core.support.graph.GraphCreator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.junit.Before;

public class BaseGraphTest {
    protected GraphCreator graphCreator = new GraphCreator();

    protected GraphDelegator graphDelegator;

    protected ListenableUndirectedWeightedGraph<Node, DefaultWeightedEdge> sampleGraph1;
    protected ListenableUndirectedWeightedGraph<Node, DefaultWeightedEdge> multipleDeviceGraph;

    protected Device device;

    protected Node sink1;
    protected Node node30;
    protected Node node135;
    protected Node node136;
    protected Node node137;

    protected Node sameAddNode1;
    protected Node sameAddNode2;

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

        device = graphCreator.getDevice();

        sink1 = graphCreator.getSink1();
        node30 = graphCreator.getSameAddNode1();
        node135 = graphCreator.getNode135();
        node136 = graphCreator.getNode136();
        node137 = graphCreator.getNode137();

        sameAddNode1 = graphCreator.getSameAddNode1();
        sameAddNode2 = graphCreator.getSameAddNode2();
    }

}
