package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DeviceMapUpdaterImplTest extends BaseMapTest {

    private DeviceMapUpdater mapUpdater;
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
        mapUpdater = new DeviceMapUpdaterImpl();

        node0 = new Node(0L);
        node1 = new Node(1L);
        node2 = new Node(2L);

        //These nodes are registered but there is no any link between them
        deviceMap.addNode(node0);
        deviceMap.addNode(node1);
        deviceMap.addNode(node2);
    }

    @Test
    public void it_should_create_new_links() throws NodeNotRegistered {
        Link l0_1 = new Link(node1,23D);
        Link l0_2 = new Link(node2,22D);
        Set<Link> links = new HashSet<>(Arrays.asList(l0_1,l0_2));
        mapUpdater.update(deviceMap,node0,links);

        assertThat(deviceMap.hasLink(node0,node1),is(true));
        assertThat(deviceMap.hasLink(node0,node2),is(true));
    }

    @Test
    public void it_should_drop_links_which_are_not_present_in_new_set() throws NodeNotRegistered {
        Link l136_135 = new Link(node135,23D);
        Set<Link> links = new HashSet<>(Arrays.asList(l136_135));

        //when we send links to updater it should drop links from 136 to 30 and from 136 to 137
        mapUpdater.update(deviceMap,node136,links);

        assertFalse(deviceMap.hasLink(node136,node30));
        assertFalse(deviceMap.hasLink(node136,node137));

        assertTrue(deviceMap.hasLink(node136,node135));
    }

    /*
        Exception tests
     */
    @Test(expected = NodeNotRegistered.class)
    public void it_should_throw_exp_if_srcNode_is_not_already_in_map() throws NodeNotRegistered {
        Set<Link> links = new HashSet<>();
        mapUpdater.update(deviceMap,new Node(2334L),links);
    }

    @Test(expected = NodeNotRegistered.class)
    public void it_should_throw_exp_if_any_of_neighbors_are_not_already_in_map() throws NodeNotRegistered {
        Set<Link> links = new HashSet<>();
        links.add(new Link(new Node(233L),23D));
        mapUpdater.update(deviceMap,node135,links);
    }

}