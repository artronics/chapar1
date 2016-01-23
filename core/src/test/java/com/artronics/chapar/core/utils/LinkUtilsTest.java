package com.artronics.chapar.core.utils;

import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LinkUtilsTest {
    private static final Double WEIGHT = 1.0D;

    private Node n1 = new Node(1L);
    private Node n2 = new Node(2L);
    private Node n3 = new Node(3L);

    private Link link1 = new Link(n1,WEIGHT);
    private Link link2 = new Link(n2,WEIGHT);
    private Link link3 = new Link(n3,WEIGHT);

    private Set<Link> set1 = new HashSet<>();
    private Set<Link> set2 = new HashSet<>();

    private Set<Link> links = new HashSet<>();

    @Test
    public void it_should_merge_what_is_not_in_set2(){
        set1.add(link1);
        set1.add(link2);

        set2.add(link3);

        links = LinkUtils.merge(set1,set2);
        assertThat(links.size(),is(equalTo(3)));
    }

    @Test
    public void it_should_also_update_weight_in_what_is_common_between_two_sets(){
        set1.add(link1);
        set1.add(link2);

        set2.add(link3);

        link1.setWeight(23D);
        set2.add(link1);//Now set2 has an updated value of link1

        links = LinkUtils.merge(set1,set2);

        links.forEach(link -> {
            if (link.equals(link1)){
                assertThat(link.getWeight(),is(equalTo(23D)));
            }

        });
    }

    @Test
    public void it_should_create_a_set_of_addedLinks(){
        set1.add(link1);
        set1.add(link2);

        set2.add(link3);

        Set<Link> addedLinks = new HashSet<>();

        LinkUtils.merge(set1,set2,addedLinks);

        assertThat(addedLinks.size(),is(equalTo(1)));
    }


}