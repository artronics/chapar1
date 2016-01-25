package com.artronics.chapar.controller.services;

import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;

import java.util.Set;

public interface NodeRegistrationService {
    void registerNode(Node srcNode,Node dstNode);

    Set<Link> registerNeighbors(Set<Link> links);
}
