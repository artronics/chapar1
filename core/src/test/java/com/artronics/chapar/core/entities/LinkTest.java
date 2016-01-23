package com.artronics.chapar.core.entities;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class LinkTest {
    private static final boolean HAPPENED = true;

    private boolean change;

    Node aNode = new Node(10L);
    Node sameNode = new Node(10L);
    Node otherNode = new Node(11L);

    Link aLink = new Link(aNode,10D);
    Link sameLink = new Link(sameNode,10D);
    Link sameLinkWithDiffWeight= new Link(sameNode,423D);
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

        assertThat(aLink,is(equalTo(sameLinkWithDiffWeight)));

        assertThat(sameLinkWithDiffWeight,is(equalTo(aLink)));
    }

    @Test
    public void if_two_links_are_equal_hash_must_be_equal(){
        assertThat(aLink.hashCode(),is(equalTo(sameLink.hashCode())));
    }

    @Test
    public void two_equal_links_with_diff_weight_should_return_same_hash(){
        assertThat(aLink.hashCode(),is(equalTo(sameLinkWithDiffWeight.hashCode())));
    }

    @Test
    public void test_in_HashSet(){
        Set<Link> links = new HashSet<>();
        links.add(aLink);
        links.add(sameLink);

        assertThat(links.size(),is(equalTo(1)));

        links.add(sameLinkWithDiffWeight);
        assertThat(links.size(),is(equalTo(1)));

        links.add(otherLink);
        assertThat(links.size(),is(equalTo(2)));
    }

    @Ignore("experimental against JDK.")
    @Test
    public void it_should_update_set_if_we_change_weight(){
        Set<Link> links = new HashSet<>();
        links.add(aLink);

        aLink.setWeight(22D);

        change = links.add(new Link(aNode,3232D));

        assertThat(change,is(HAPPENED));
    }

}