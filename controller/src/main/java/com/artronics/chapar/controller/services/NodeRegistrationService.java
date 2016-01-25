package com.artronics.chapar.controller.services;

import com.artronics.chapar.core.entities.Node;

public interface NodeRegistrationService {
    void registerNode(Node srcNode,Node dstNode);
}
