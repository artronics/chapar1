package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.NodeRegistrationService;
import com.artronics.chapar.core.entities.Node;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class NodeRegistrationServiceImpl implements NodeRegistrationService{
    private final static Logger log = Logger.getLogger(NodeRegistrationServiceImpl.class);

    @Override
    public Node registerNode(Node node) {
        return null;
    }
}
