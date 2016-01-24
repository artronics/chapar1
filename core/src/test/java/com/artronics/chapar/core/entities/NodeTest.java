package com.artronics.chapar.core.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class NodeTest {

    Device device = new Device(100L);

    Node aNode;
    Node sameNode;
    Node otherNode;

    @Before
    public void setUp() throws Exception
    {
        aNode =Node.create(Address.create(device,10L));
        sameNode = Node.create(Address.create(device,10L));
        otherNode = Node.create(Address.create(device,11L));
    }

    @Test
    public void test_equals_general(){
        assertTrue(aNode.equals(aNode));
        assertThat(aNode,equalTo(sameNode));
        assertThat(sameNode,equalTo(aNode));

        assertFalse(aNode.equals(otherNode));
        assertFalse(otherNode.equals(aNode));

        Node otherSameNode = Node.create(aNode.getAddress());
        assertThat(sameNode,equalTo(otherSameNode));

        Node nullNode = null;
        assertFalse(aNode.equals(nullNode));
    }

    @Test
    public void two_nodes_with_diff_devices_are_not_eq(){
        aNode.getAddress().setDevice(new Device(244L));
        assertThat(aNode,is(not(equalTo(sameNode))));
    }
    @Test
    public void test_hashCode(){
        assertThat(aNode.hashCode(),equalTo(sameNode.hashCode()));
    }

    @Test
    public void test_set(){
        Set<Node> nodes = new HashSet<>();
        nodes.add(aNode);
        nodes.add(sameNode);

        Node otherSameNode = Node.create(aNode.getAddress());

        assertThat(nodes.size(),equalTo(1));
        assertTrue(nodes.contains(otherSameNode));

    }

}