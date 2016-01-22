package com.artronics.chapar.core.repositories;

import com.artronics.chapar.core.entities.Node;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NodeRepoTest extends BaseRepoTest{
    @Autowired
    private NodeRepo nodeRepo;

    @Test
    public void it_should_persist_a_node(){
        Node node = new Node(34L);
        nodeRepo.save(node);
    }
}