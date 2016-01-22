package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public class DeviceMapUpdaterImplTest extends BaseMapTest{

    private DeviceMapUpdater mapUpdater;

    private Node node0;
    private Node node1;
    private Node node2;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mapUpdater = new DeviceMapUpdaterImpl();

        node0 = new Node(0L);
        node1 = new Node(1L);
        node2 = new Node(2L);

        //These nodes are registered but there is no any link between them
        deviceMap.addNode(node0);
        deviceMap.addNode(node1);
        deviceMap.addNode(node2);
    }

    @Test(expected = NodeNotRegistered.class)
    public void it_should_throw_exp_if_srcNode_is_not_already_in_map() throws NodeNotRegistered {
        Set<Node> neighbors = deviceMap.getNeighbors(node135);//Just a set
        mapUpdater.update(deviceMap,new Node(2334L),neighbors);
    }

    @Test(expected = NodeNotRegistered.class)
    public void it_should_throw_exp_if_any_of_neighbors_are_not_already_in_map() throws NodeNotRegistered {
        Set<Node> neighbors = deviceMap.getNeighbors(node135);
        neighbors.add(new Node(123L));//Add a node to neighbors but it is not already in network
        mapUpdater.update(deviceMap,node135,neighbors);
    }
    
    @Test
    public void it_should_create_new_links() throws NodeNotRegistered {
        Set<Node> neighbors = new HashSet<>(Arrays.asList(node1,node2));
        mapUpdater.update(deviceMap,node0,neighbors);

    }
}