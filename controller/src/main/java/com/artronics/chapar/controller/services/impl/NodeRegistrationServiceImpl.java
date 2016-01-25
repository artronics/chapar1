package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.NodeRegistrationService;
import com.artronics.chapar.core.entities.Node;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class NodeRegistrationServiceImpl implements NodeRegistrationService{
    private final static Logger log = Logger.getLogger(NodeRegistrationServiceImpl.class);

    private Map<Node,Node> registeredNodes;

    @Override
    public void registerNode(Node srcNode, Node dstNode) {
        if (!registeredNodes.containsKey(srcNode)){
            log.debug("Registering new source node: "+srcNode);
            srcNode.setStatus(Node.Status.ACTIVE);
            registeredNodes.put(srcNode,srcNode);
        }
        else if (registeredNodes.get(srcNode).getStatus()!= Node.Status.ACTIVE){
           Node n = registeredNodes.get(srcNode);
            log.debug("Changing "+n+" Status from: "+srcNode.getStatus()+" to "+Node.Status.ACTIVE);
            n.setStatus(Node.Status.ACTIVE);
            registeredNodes.put(srcNode,n);
        }

        if (!registeredNodes.containsKey(dstNode)){
            log.debug("Registering new destination node: "+dstNode);
            dstNode.setStatus(Node.Status.IDLE);
            registeredNodes.put(dstNode,dstNode);
        }

    }

    @Resource(name = "registeredNodes")
    public void setRegisteredNodes(Map<Node,Node> registeredNodes) {
        this.registeredNodes = registeredNodes;
    }
}
