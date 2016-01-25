package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NodeMapBasicTest {
    private final static Logger log = Logger.getLogger(NodeMapBasicTest.class);

    private NodeMapUpdaterImpl mapUpdater;

    private NodeMap nodeMap;

    Device device = new Device(234L);

    private Node node0;
    private Node node1;
    private Node node2;

    @Before
    public void setUp() throws Exception {
        mapUpdater = new NodeMapUpdaterImpl();

        nodeMap = new NodeMapImpl();
        mapUpdater.setNodeMap(nodeMap);

        node0 = Node.create(Address.create(device,0L));
        node1 = Node.create(Address.create(device,1L));
        node2 = Node.create(Address.create(device,2L));

        //These nodes are registered but there is no any link between them
        nodeMap.addNode(node0);
        nodeMap.addNode(node1);
        nodeMap.addNode(node2);
    }
    /*
        General behaviour
     */
    @Test
    public void it_should_create_new_links() throws NodeNotRegistered {
        Link l0_1 = new Link(node1,23D);
        Link l0_2 = new Link(node2,22D);
        Set<Link> links = new HashSet<>(Arrays.asList(l0_1,l0_2));
        mapUpdater.update(nodeMap,node0,links);

        assertThat(nodeMap.hasLink(node0,node1),is(true));
        assertThat(nodeMap.hasLink(node0,node2),is(true));

        assertThat(nodeMap.hasLink(node1,node0),is(true));
        assertThat(nodeMap.hasLink(node2,node0),is(true));
    }

}
