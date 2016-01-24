package com.artronics.chapar.core.map.graph;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.RouteNotFound;
import com.artronics.chapar.core.map.BaseGraphTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JGraphTDelegatorTest extends BaseGraphTest {

    private Node notExistNode;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        notExistNode = Node.create(Address.create(device,2323L));
    }

    @Test(expected = IllegalStateException.class)
    public void it_should_throw_exp_if_node_does_not_exist() {
        assertNull(graphDelegator.getNeighbors(notExistNode));
    }

    @Test
    public void test_isIsland() {
        sampleGraph1.addVertex(notExistNode);

        assertTrue(graphDelegator.isIsland(notExistNode));
        assertFalse(graphDelegator.isIsland(node135));
    }

    @Test
    public void It_should_give_the_shortest_path() throws RouteNotFound {
        List<Node> path = graphDelegator.getShortestPath(node30, node137);

        assertThat(path.size(), equalTo(4));
        assertThat(path.get(0).getAddress().getLocalAdd(), equalTo(30L));
        assertThat(path.get(1).getAddress().getLocalAdd(), equalTo(135L));
        assertThat(path.get(2).getAddress().getLocalAdd(), equalTo(136L));
        assertThat(path.get(3).getAddress().getLocalAdd(), equalTo(137L));
    }

    @Test
    public void it_should_return_a_set_of_neighbors_not_containing_itself() {
        Set<Node> neighbors = graphDelegator.getNeighbors(node135);


        //should not contain itself
        assertFalse(neighbors.contains(node135));

        assertFalse(neighbors.contains(node137));

        assertTrue(neighbors.contains(node30));
        assertTrue(neighbors.contains(sink1));
        assertTrue(neighbors.contains(node136));
    }

    @Test
    public void it_should_return_empty_set_if_node_has_no_neighbors() {
        sampleGraph1.addVertex(notExistNode);
        Set<Node> nodes = graphDelegator.getNeighbors(notExistNode);
        assertTrue(nodes.isEmpty());
    }

    @Test
    public void it_should_getWeight_of_two_nodes() {
        Double weight = graphDelegator.getWeigh(node135, node136);
        assertThat(weight, is(equalTo(25D)));
    }

    @Test
    public void getWeigh_should_be_mutaul_between_src_and_dst_node() {
        Double weight = graphDelegator.getWeigh(node136, node135);
        assertThat(weight, is(equalTo(25D)));
    }

}