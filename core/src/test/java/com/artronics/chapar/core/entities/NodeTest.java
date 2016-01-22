package com.artronics.chapar.core.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class NodeTest {

    Node aNode = new Node(10L);
    Node sameNode = new Node(10L);
    Node otherNode = new Node(11L);

    @Before
    public void setUp() throws Exception
    {
    }

    @Test
    public void test_equals_general(){
        assertTrue(aNode.equals(aNode));
        assertThat(aNode,equalTo(sameNode));
        assertThat(sameNode,equalTo(aNode));

        assertFalse(aNode.equals(otherNode));
        assertFalse(otherNode.equals(aNode));

        Node otherSameNode = new Node(aNode.getAddress());
        assertThat(sameNode,equalTo(otherSameNode));

        Node nullNode = null;
        assertFalse(aNode.equals(nullNode));
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

        Node otherSameNode = new Node(aNode.getAddress());

        assertThat(nodes.size(),equalTo(1));
        assertTrue(nodes.contains(otherSameNode));

    }

}