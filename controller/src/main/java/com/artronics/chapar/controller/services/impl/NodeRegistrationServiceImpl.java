package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.NodeRegistrationService;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.map.NodeMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

@Service
public class NodeRegistrationServiceImpl implements NodeRegistrationService{
    private final static Logger log = Logger.getLogger(NodeRegistrationServiceImpl.class);

    private Map<Node,Node> registeredNodes;

    private NodeMap nodeMap;

    @Override
    public void registerNode(Node srcNode, Node dstNode) {
        if (!registeredNodes.containsKey(srcNode)){
            log.debug("Registering new source node: "+srcNode);
            srcNode.setStatus(Node.Status.ACTIVE);
            registeredNodes.put(srcNode,srcNode);
            //Look at ControllerUpdaterIT test there is a bug here which the map vertex
            //doesn't update the status of node. There is no way to get the vertex object from
            //graph and update it's value.
            nodeMap.addNode(srcNode);
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
            nodeMap.addNode(dstNode);
        }
    }

    @Override
    public Set<Link> registerNeighbors(Set<Link> links) {
        links.forEach(link -> {
            Node neighbor = link.getDstNode();
            if (!nodeMap.contains(neighbor)){
                log.debug("Registering new Neighbor "+ neighbor);
//                neighbor.setStatus(Node.Status.UNREGISTERED);
                nodeMap.addNode(neighbor);
            }
        });

        return links;
    }

    @Resource(name = "nodeMap")
    public void setNodeMap(NodeMap nodeMap) {
        this.nodeMap = nodeMap;
    }

    @Resource(name = "registeredNodes")
    public void setRegisteredNodes(Map<Node,Node> registeredNodes) {
        this.registeredNodes = registeredNodes;
    }
}
