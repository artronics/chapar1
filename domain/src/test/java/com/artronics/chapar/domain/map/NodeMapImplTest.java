package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class NodeMapImplTest extends BaseMapTest {

    //TODO what happens if we receive a link from source to source

    @Test
    public void
    If_we_add_link_to_a_node_which_already_exists_it_should_ignore_it()
    {
        //null check on addLink otherwise jgrapht throws exp
        nodeMap.addLink(sensor136, sensor135, 10);
    }

    @Test
    public void It_should_return_true_if_a_node_hasLink_to_other_node()
    {
        assertTrue(nodeMap.hasLink(sensor135, sensor136));
        assertTrue(nodeMap.hasLink(sensor136, sensor135));

        assertFalse(nodeMap.hasLink(sensor135, sensor137));
    }

    @Test
    public void It_should_return_false_if_we_ask_a_node_hasLink_to_itself()
    {
        assertThat(nodeMap.hasLink(sensor135, sensor135), equalTo(false));
    }

    @Test
    public void Test_contains_node()
    {
        assertThat(nodeMap.contains(sensor135), equalTo(true));
    }

    @Test
    public void test_remove_node()
    {
        assertTrue(nodeMap.contains(sensor137));
        nodeMap.removeNode(sensor137);
        assertFalse(nodeMap.contains(sensor137));
    }

    @Test
    public void test_remove_link(){
        assertTrue(nodeMap.hasLink(sensor137, sensor136));
        nodeMap.removeLink(sensor136, sensor137);
        assertFalse(nodeMap.hasLink(sensor137, sensor136));
    }

    @Test
    public void Two_nodes_with_same_address_and_same_deviceId_are_equal()
    {
        Sensor eqSensor135 = Sensor.create(UnicastAddress.create(client,135L));
        assertThat(nodeMap.contains(eqSensor135), equalTo(true));
    }

    @Test
    public void test_getAllNodes()
    {
        List<Sensor> sensors = nodeMap.getAllNodes();
        assertThat(sensors.size(), equalTo(5));
    }

    @Test
    public void it_should_get_neighbors(){
        Set<Sensor> neighbors = nodeMap.getNeighbors(sensor135);
        assertThat(neighbors.size(),equalTo(3));
    }

    @Test
    public void it_should_give_all_links_to_a_node(){
        Set<SensorLink> links = nodeMap.getLinks(sensor135);
        assertThat(links.size(),is(equalTo(3)));
    }
    
    @Test
    public void it_should_get_DEF_WEIGHT_if_src_and_dst_are_equal(){
        Double weight = nodeMap.getWeigh(sensor136, sensor136);
        assertThat(weight,is(equalTo(NodeMap.DEF_WEIGHT)));
    }

}