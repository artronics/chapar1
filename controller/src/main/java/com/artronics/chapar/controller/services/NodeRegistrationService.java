package com.artronics.chapar.controller.services;

import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.AddressConflictException;

import java.util.Set;

public interface NodeRegistrationService {
    Node registerSink(Long sinkAddress,Device device) throws AddressConflictException;

    void registerSrcDstNodesInPacket(Node srcNode, Node dstNode);

    Set<Link> registerNeighbors(Set<Link> links);
}
