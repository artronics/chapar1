package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class NodeMapUpdaterImplTest extends BaseMapTest {

    private NodeMapUpdater mapUpdater;
    private Node node0;
    private Node node1;
    private Node node2;

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
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mapUpdater = new NodeMapUpdaterImpl();


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

    @Test
    public void it_should_drop_links_which_are_not_present_in_new_set() throws NodeNotRegistered {
        Link l136_135 = new Link(node135,23D);
        Set<Link> links = new HashSet<>(Arrays.asList(l136_135));

        //when we send links to updater it should drop links from 136 to 30 and from 136 to 137
        mapUpdater.update(nodeMap,node136,links);

        assertFalse(nodeMap.hasLink(node136,node30));
        assertFalse(nodeMap.hasLink(node136,node137));

        assertTrue(nodeMap.hasLink(node136,node135));
    }

    @Test
    public void it_should_update_weight_value_for_links() throws NodeNotRegistered {
        Link l136_135 = new Link(node135,230D);
        Set<Link> links = new HashSet<>(Arrays.asList(l136_135));

        //when we send links to updater it should drop links from 136 to 30 and from 136 to 137
        mapUpdater.update(nodeMap,node136,links);

        Double weight = nodeMap.getWeigh(node136,node135);
        assertThat(weight,is(equalTo(230D)));

        weight = nodeMap.getWeigh(node135,node136);
        assertThat(weight,is(equalTo(230D)));
    }
    
    @Test
    public void it_should_find_island_nodes() throws NodeNotRegistered {
        Link l136_135 = new Link(node135,230D);
        Set<Link> links = new HashSet<>(Arrays.asList(l136_135));

        //when we send links to updater it should drop links from 136 to 30 and from 136 to 137
        //then node 137 must be island
        Set<Node> islands = new HashSet<>();
        mapUpdater.update(nodeMap,node136,links,islands);

        assertThat(islands.size(),is(equalTo(1)));
        assertTrue(islands.contains(node137));
    }

    /*
        Detailed Tests
     */
    @Test
    public void it_should_not_touch_unrelated_links() throws NodeNotRegistered {
        Link l136_135 = new Link(node135,23D);
        Set<Link> links = new HashSet<>(Arrays.asList(l136_135));

        mapUpdater.update(nodeMap,node136,links);
        //we want to update node136 links. other nodes must be as it was
        assertTrue(nodeMap.contains(node135));
        assertTrue(nodeMap.contains(node136));
        assertTrue(nodeMap.contains(node137));
        assertTrue(nodeMap.contains(node30));

        assertTrue(nodeMap.hasLink(node135,node30));
        assertTrue(nodeMap.hasLink(sink1,node30));

        assertThat(nodeMap.getWeigh(node135,node30),is(equalTo(20D)));
    }

    /*
        Exception tests
     */
    @Test(expected = NodeNotRegistered.class)
    public void it_should_throw_exp_if_srcNode_is_not_already_in_map() throws NodeNotRegistered {
        Set<Link> links = new HashSet<>();
        mapUpdater.update(nodeMap,Node.create(Address.create(device,2324L)),links);
    }

    @Test(expected = NodeNotRegistered.class)
    public void it_should_throw_exp_if_any_of_neighbors_are_not_already_in_map() throws NodeNotRegistered {
        Set<Link> links = new HashSet<>();
        links.add(new Link(Node.create(Address.create(device,4224L)),23D));
        mapUpdater.update(nodeMap,node135,links);
    }

}