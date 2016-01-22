package com.artronics.chapar.core.map.graph;

import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.support.graph.GraphCreator;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class JGraphTDelegatorTest {

    private GraphCreator graphCreator = new GraphCreator();

    private GraphDelegator graphDelegator;

    Graph<Node, DefaultWeightedEdge> sampleGraph1;

    protected Node sink1;
    protected Node node30;
    protected Node node135;
    protected Node node136;
    protected Node node137;

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
        graphDelegator = new JGraphTDelegator(sampleGraph1);

        sink1 = graphCreator.getSink1();
        node30 = graphCreator.getSameAddNode1();
        node135 = graphCreator.getNode135();
        node136 = graphCreator.getNode136();
        node137 = graphCreator.getNode137();
    }

    @Test(expected = IllegalStateException.class)
    public void it_should_throw_exp_if_node_does_not_exist()
    {
        Node node = new Node(3432L);
        assertNull(graphDelegator.getNeighbors(node));
    }

    @Test
    public void test_isIsland()
    {
        Node node = new Node(3432L);
        sampleGraph1.addVertex(node);

        assertTrue(graphDelegator.isIsland(node));
        assertFalse(graphDelegator.isIsland(node135));
    }

    @Test
    public void It_should_give_the_shortest_path()
    {
        List<Node> path = graphDelegator.getShortestPath(node30, node137);

        assertThat(path.size(), equalTo(4));
        assertThat(path.get(0).getAddress(),equalTo(30L));
        assertThat(path.get(1).getAddress(),equalTo(135L));
        assertThat(path.get(2).getAddress(),equalTo(136L));
        assertThat(path.get(3).getAddress(),equalTo(137L));
    }


}