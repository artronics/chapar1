package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;
import com.artronics.chapar.core.utils.LinkUtils;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class DeviceMapUpdaterImpl implements DeviceMapUpdater {
    private final static Logger log = Logger.getLogger(DeviceMapUpdaterImpl.class);

    @Override
    public void update(NodeMap nodeMap, Node srcNode, Set<Link> links, Set<Node> islandNodes) throws NodeNotRegistered {

        checkIfNodesAreRegistered(nodeMap,srcNode,links);

        Set<Link> oldLinks = nodeMap.getLinks(srcNode);
        Set<Link> mergedLinks = LinkUtils.merge(oldLinks,links);

        Set<Link> removedLinks = new HashSet<>(oldLinks);
        removedLinks.removeAll(links);

        mergedLinks.forEach(link -> {
            Node dstNode = link.getDstNode();
            if (removedLinks.contains(link)){
                nodeMap.removeLink(srcNode, dstNode);
                if (islandNodes!=null && nodeMap.isIsland(dstNode)) {
                    islandNodes.add(dstNode);
                }
            }
            else {
                nodeMap.addLink(srcNode, dstNode,link.getWeight());
            }
        });
    }

    @Override
    public void update(NodeMap nodeMap, Node srcNode, Set<Link> links) throws NodeNotRegistered {
        update(nodeMap,srcNode,links,null);
    }

    private static void checkIfNodesAreRegistered(NodeMap nodeMap, Node srcNode, Set<Link> links) throws NodeNotRegistered {
        log.debug("Check if source node is registered.");
        if (!nodeMap.contains(srcNode))
            throw new NodeNotRegistered();

        log.debug("Check if link's destination node is registered.");
        if (links.stream().anyMatch(link -> !nodeMap.contains(link.getDstNode())))
            throw new NodeNotRegistered();
    }

}
