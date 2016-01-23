package com.artronics.chapar.core.entities;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
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
        assertThat(sameLink,is(equalTo(aLink)));

        assertThat(aLink,is(not(equalTo(otherLink))));
    }

    @Test
    public void for_equality_weight_is_not_important(){
        Link sameLinkWithDiffWeight = new Link(aNode,233D);

        assertThat(aLink,is(equalTo(sameLinkWithDiffWeight)));

        assertThat(sameLinkWithDiffWeight,is(equalTo(aLink)));
    }

    @Test
    public void if_two_links_are_equal_hash_must_be_equal(){
        assertThat(aLink.hashCode(),is(equalTo(sameLink.hashCode())));
    }

    @Test
    public void test_in_HashSet(){
        Set<Link> links = new HashSet<>();
        links.add(aLink);
        links.add(sameLink);

        assertThat(links.size(),is(equalTo(1)));

        Link sameLinkWithDiffWeight = new Link(aNode,233D);
        links.add(sameLinkWithDiffWeight);
        assertThat(links.size(),is(equalTo(1)));

        links.add(otherLink);
        assertThat(links.size(),is(equalTo(2)));
    }

}