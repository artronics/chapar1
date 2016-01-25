package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;

import java.util.Set;

public interface NodeMapUpdater {

    void update(NodeMap nodeMap, Node srcNode, Set<Link> links, Set<Node> islandNodes) throws NodeNotRegistered;

    void update(NodeMap nodeMap, Node srcNode, Set<Link> links) throws NodeNotRegistered;
}
