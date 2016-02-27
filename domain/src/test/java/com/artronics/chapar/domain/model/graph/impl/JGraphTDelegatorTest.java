package com.artronics.chapar.domain.model.graph.impl;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.exceptions.RouteNotFound;
import com.artronics.chapar.domain.map.BaseGraphTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JGraphTDelegatorTest extends BaseGraphTest{
    private Sensor notExistSensor;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        notExistSensor = Sensor.create(UnicastAddress.create(client,2323L));
    }

    @Test(expected = IllegalStateException.class)
    public void it_should_throw_exp_if_node_does_not_exist() {
        assertNull(graphDelegator.getNeighbors(notExistSensor));
    }

    @Test
    public void test_isIsland() {
        sampleGraph1.addVertex(notExistSensor);

        assertTrue(graphDelegator.isIsland(notExistSensor));
        assertFalse(graphDelegator.isIsland(sensor135));
    }

    @Test
    public void It_should_give_the_shortest_path() throws RouteNotFound {
        List<Sensor> path = graphDelegator.getShortestPath(sensor30, sensor137);

        assertThat(path.size(), equalTo(4));
        assertThat(path.get(0).getAddress().getLocalAddress(), equalTo(30L));
        assertThat(path.get(1).getAddress().getLocalAddress(), equalTo(135L));
        assertThat(path.get(2).getAddress().getLocalAddress(), equalTo(136L));
        assertThat(path.get(3).getAddress().getLocalAddress(), equalTo(137L));
    }

    @Test
    public void it_should_return_a_set_of_neighbors_not_containing_itself() {
        Set<Sensor> neighbors = graphDelegator.getNeighbors(sensor135);


        //should not contain itself
        assertFalse(neighbors.contains(sensor135));

        assertFalse(neighbors.contains(sensor137));

        assertTrue(neighbors.contains(sensor30));
        assertTrue(neighbors.contains(sink1));
        assertTrue(neighbors.contains(sensor136));
    }

    @Test
    public void it_should_return_empty_set_if_node_has_no_neighbors() {
        sampleGraph1.addVertex(notExistSensor);
        Set<Sensor> sensors = graphDelegator.getNeighbors(notExistSensor);
        assertTrue(sensors.isEmpty());
    }

    @Test
    public void it_should_getWeight_of_two_nodes() {
        Double weight = graphDelegator.getWeigh(sensor135, sensor136);
        assertThat(weight, is(equalTo(25D)));
    }

    @Test
    public void getWeigh_should_be_mutaul_between_src_and_dst_node() {
        Double weight = graphDelegator.getWeigh(sensor136, sensor135);
        assertThat(weight, is(equalTo(25D)));
    }

}