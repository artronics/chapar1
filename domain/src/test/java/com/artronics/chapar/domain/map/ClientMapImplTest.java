package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ClientMapImplTest extends BaseMapTest {

    @Test
    public void
    If_we_add_link_to_a_node_which_already_exists_it_should_ignore_it()
    {
        //null check on addLink otherwise jgrapht throws exp
        deviceMap.addLink(sensor136, sensor135, 10);
    }

    @Test
    public void It_should_return_true_if_a_node_hasLink_to_other_node()
    {
        assertTrue(deviceMap.hasLink(sensor135, sensor136));
        assertTrue(deviceMap.hasLink(sensor136, sensor135));

        assertFalse(deviceMap.hasLink(sensor135, sensor137));
    }

    @Test
    public void It_should_return_false_if_we_ask_a_node_hasLink_to_itself()
    {
        assertThat(deviceMap.hasLink(sensor135, sensor135), equalTo(false));
    }

    @Test
    public void Test_contains_node()
    {
        assertThat(deviceMap.contains(sensor135), equalTo(true));
    }

    @Test
    public void test_remove_node()
    {
        deviceMap.removeNode(sensor137);
        assertFalse(deviceMap.contains(sensor137));
    }

    @Test
    public void test_remove_link(){
        deviceMap.removeLink(sensor136, sensor137);
        assertFalse(deviceMap.hasLink(sensor137, sensor136));
    }

    @Test
    public void Two_nodes_with_same_address_are_equal()
    {
        Sensor eqSensor135 = new Sensor(135L);
        assertThat(deviceMap.contains(eqSensor135), equalTo(true));
    }

    @Test
    public void test_getAllNodes()
    {
        List<Sensor> sensors = deviceMap.getAllNodes();
        assertThat(sensors.size(), equalTo(5));
    }

    @Test
    public void it_should_get_neighbors(){
        Set<Sensor> neighbors = deviceMap.getNeighbors(sensor135);
        assertThat(neighbors.size(),equalTo(3));
    }
}