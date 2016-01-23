package com.artronics.chapar.core.entities;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LinkTest {

    Node aNode = new Node(10L);
    Node sameNode = new Node(10L);
    Node otherNode = new Node(11L);

    Link aLink = new Link(aNode,10D);
    Link sameLink = new Link(sameNode,123D);
    Link otherLink = new Link(otherNode,233D);

    @Test
    public void equal_general_test(){
        assertThat(aLink,is(equalTo(aLink)));

        assertThat(aLink,is(equalTo(sameLink)));
    }

}