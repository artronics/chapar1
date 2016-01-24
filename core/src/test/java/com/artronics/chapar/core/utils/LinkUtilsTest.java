package com.artronics.chapar.core.utils;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LinkUtilsTest {
    private static final Double WEIGHT = 1.0D;

    private final LinkUtils linkUtils =new LinkUtils();

    private Device device = new Device(299L);

    private Node n1;
    private Node n2;
    private Node n3;

    private Link link1 = new Link(n1,WEIGHT);
    private Link link2 = new Link(n2,WEIGHT);
    private Link link3 = new Link(n3,WEIGHT);

    private Set<Link> oldSet = new HashSet<>();
    private Set<Link> newSet = new HashSet<>();

    private Set<Link> links = new HashSet<>();

    @Before
    public void setUp() throws Exception {
        n1 = Node.create(Address.create(device,1L));
        n2 = Node.create(Address.create(device,2L));
        n3 = Node.create(Address.create(device,3L));
    }

    @After
    public void tearDown() throws Exception {
        links.clear();
        oldSet.clear();
        newSet.clear();
    }

    @Ignore("not implemented")
    @Test
    public void it_should_get_a_list_of_removed_links(){
        oldSet.add(link1);
        oldSet.add(link2);

        newSet.add(link3);
        Set<Link> expRemovedList = new HashSet<>(Arrays.asList(link2));

        links = LinkUtils.getRemovedLinks(oldSet, newSet);
        assertThat(links,is(equalTo(expRemovedList)));
    }

    @Test
    public void it_should_merge_what_is_not_in_set2(){
        oldSet.add(link1);
        oldSet.add(link2);

        newSet.add(link3);

        links = LinkUtils.merge(oldSet, newSet);
        assertThat(links.size(),is(equalTo(3)));
    }

    @Test
    public void it_should_also_update_weight_in_what_is_common_between_two_sets(){
        oldSet.add(link1);
        oldSet.add(link2);

        newSet.add(link3);

        // create a new link with n1 and diff weight
        Link link1_new = new Link(n1,23D);
        newSet.add(link1_new);//Now newSet has an updated value of link1

        links = LinkUtils.merge(oldSet, newSet);

        links.forEach(link -> {
            if (link.equals(link1)){
                assertThat(link.getWeight(),is(equalTo(23D)));
            }

        });
    }

    @Test
    public void it_should_create_a_set_of_addedLinks(){
        oldSet.add(link1);
        oldSet.add(link2);

        newSet.add(link3);

        Set<Link> addedLinks = new HashSet<>();

        LinkUtils.merge(oldSet, newSet,addedLinks);

        assertThat(addedLinks.size(),is(equalTo(1)));
    }

    @Test
    public void addedLinks_should_NOT_contains_those_links_with_same_node_but_diff_weight(){
        oldSet.add(link1);
        oldSet.add(link2);

        newSet.add(link3);
        // create a new link with n1 and diff weight
        Link link1_new = new Link(n1,23D);
        newSet.add(link1_new);//Now newSet has an updated value of link1

        Set<Link> addedLinks = new HashSet<>();

        LinkUtils.merge(oldSet, newSet,addedLinks);

        //newSet has link1_new but the addedLinks size must remain 1-> link3
        assertThat(addedLinks.size(),is(equalTo(1)));
    }


}