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

public class NodeRepoLinksTest extends BaseRepoTest{

    @Autowired
    private NodeRepo nodeRepo;

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
        NodeLink l0_1=new NodeLink(n1,23D);
        NodeLink l0_2=new NodeLink(n2,32D);
        List<NodeLink> l= new ArrayList<>(Arrays.asList(l0_1,l0_2));
    }
}