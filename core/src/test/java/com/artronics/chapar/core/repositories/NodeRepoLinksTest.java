package com.artronics.chapar.core.repositories;

import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.entities.NodeLink;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NodeRepoLinksTest extends BaseRepoTest{

    @Autowired
    private NodeRepo nodeRepo;

    @Autowired
    private NodeLinkRepo linkRepo;

    private Node node0;
    private Node n1;
    private Node n2;

    @Before
    public void setUp() throws Exception {
        node0 = new Node(0L);
        n1 = new Node(1L);
        n2 = new Node(2L);

        nodeRepo.save(node0);
        nodeRepo.save(n1);
        nodeRepo.save(n2);
    }

    @Test
    @Rollback(value = false)
    public void it_should_persist_links_to_node(){
        NodeLink l0_1=new NodeLink(node0,n1,23D);
        NodeLink l0_2=new NodeLink(node0,n2,32D);
        List<NodeLink> links= new ArrayList<>(Arrays.asList(l0_1,l0_2));
//        linkRepo.save(links);
//        node0.setLinks(links);
        node0.addLink(l0_1);
        node0.addLink(l0_2);
        nodeRepo.save(node0);
//
//        NodeLink l0_1_2=new NodeLink(n1,3455D);
//        NodeLink l0_2_2=new NodeLink(n2,34222D);
//        List<NodeLink> links2= new ArrayList<>(Arrays.asList(l0_1_2,l0_2_2));
//        linkRepo.save(links2);
//        node0.setLinks(links2);
//        nodeRepo.save(node0);

        Node persistedNode = nodeRepo.findOne(node0.getId());
        assertThat(persistedNode.getLinks().size(),is(equalTo(2)));
    }


}