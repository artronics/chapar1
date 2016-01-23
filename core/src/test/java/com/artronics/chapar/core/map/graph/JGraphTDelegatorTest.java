package com.artronics.chapar.core.map.graph;

import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.map.BaseGraphTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class JGraphTDelegatorTest extends BaseGraphTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
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

    @Test
    public void it_should_return_a_set_of_neighbors_not_containing_itself()
    {
        Set<Node> neighbors = graphDelegator.getNeighbors(node135);


        //should not contain itself
        assertFalse(neighbors.contains(node135));

        assertFalse(neighbors.contains(node137));

        assertTrue(neighbors.contains(node30));
        assertTrue(neighbors.contains(sink1));
        assertTrue(neighbors.contains(node136));
    }

    @Test
    public void it_should_return_empty_set_if_node_has_no_neighbors()
    {
        Node node = new Node(3432L);
        sampleGraph1.addVertex(node);
        Set<Node> nodes = graphDelegator.getNeighbors(node);
        assertTrue(nodes.isEmpty());
    }

    @Test
    public void it_should_getWeight_of_two_nodes(){
        Double weight = graphDelegator.getWeigh(node135,node136);
        assertThat(weight,is(equalTo(25D)));
    }

}