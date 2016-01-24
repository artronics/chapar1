package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DeviceMapImplTest extends BaseMapTest {

    @Test
    public void
    If_we_add_link_to_a_node_which_already_exists_it_should_ignore_it()
    {
        //null check on addLink otherwise jgrapht throws exp
        deviceMap.addLink(node136, node135, 10);
    }

    @Test
    public void It_should_return_true_if_a_node_hasLink_to_other_node()
    {
        assertTrue(deviceMap.hasLink(node135, node136));
        assertTrue(deviceMap.hasLink(node136, node135));

        assertFalse(deviceMap.hasLink(node135, node137));
    }

    @Test
    public void It_should_return_false_if_we_ask_a_node_hasLink_to_itself()
    {
        assertThat(deviceMap.hasLink(node135, node135), equalTo(false));
    }

    @Test
    public void Test_contains_node()
    {
        assertThat(deviceMap.contains(node135), equalTo(true));
    }

    @Test
    public void test_remove_node()
    {
        deviceMap.removeNode(node137);
        assertFalse(deviceMap.contains(node137));
    }

    @Test
    public void test_remove_link(){
        deviceMap.removeLink(node136, node137);
        assertFalse(deviceMap.hasLink(node137, node136));
    }

    @Test
    public void Two_nodes_with_same_address_are_equal()
    {
        Node eqNode135= Node.create(Address.create(new Device(23L),135L));
        assertThat(deviceMap.contains(eqNode135), equalTo(true));
    }

    @Test
    public void test_getAllNodes()
    {
        List<Node> nodes = deviceMap.getAllNodes();
        assertThat(nodes.size(), equalTo(5));
    }

    @Test
    public void it_should_get_neighbors(){
        Set<Node> neighbors = deviceMap.getNeighbors(node135);
        assertThat(neighbors.size(),equalTo(3));
    }

    @Test
    public void it_should_give_all_links_to_a_node(){
        Set<Link> links = deviceMap.getLinks(node135);
        assertThat(links.size(),is(equalTo(3)));
    }
    
    @Test
    public void it_should_get_DEF_WEIGHT_if_src_and_dst_are_equal(){
        Double weight = deviceMap.getWeigh(node136,node136);
        assertThat(weight,is(equalTo(DeviceMap.DEF_WEIGHT)));
    }

}