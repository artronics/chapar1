package com.artronics.chapar.domain.map.graph;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.map.BaseGraphTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

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
        Sensor sensor = new Sensor(3432L);
        assertNull(graphDelegator.getNeighbors(sensor));
    }

    @Test
    public void test_isIsland()
    {
        Sensor sensor = new Sensor(3432L);
        sampleGraph1.addVertex(sensor);

        assertTrue(graphDelegator.isIsland(sensor));
        assertFalse(graphDelegator.isIsland(sensor135));
    }

    @Test
    public void It_should_give_the_shortest_path()
    {
        List<Sensor> path = graphDelegator.getShortestPath(sensor30, sensor137);

        assertThat(path.size(), equalTo(4));
        assertThat(path.get(0).getAddress(),equalTo(30L));
        assertThat(path.get(1).getAddress(),equalTo(135L));
        assertThat(path.get(2).getAddress(),equalTo(136L));
        assertThat(path.get(3).getAddress(),equalTo(137L));
    }

    @Test
    public void it_should_return_a_set_of_neighbors_not_containing_itself()
    {
        Set<Sensor> neighbors = graphDelegator.getNeighbors(sensor135);


        //should not contain itself
        assertFalse(neighbors.contains(sensor135));

        assertFalse(neighbors.contains(sensor137));

        assertTrue(neighbors.contains(sensor30));
        assertTrue(neighbors.contains(sink1));
        assertTrue(neighbors.contains(sensor136));
    }

    @Test
    public void it_should_return_empty_set_if_node_has_no_neighbors()
    {
        Sensor sensor = new Sensor(3432L);
        sampleGraph1.addVertex(sensor);
        Set<Sensor> sensors = graphDelegator.getNeighbors(sensor);
        assertTrue(sensors.isEmpty());
    }

}