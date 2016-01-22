package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;

import java.util.Set;

public interface DeviceMapUpdater {
    void update(DeviceMap deviceMap, Node srcNode, Set<Node> newNeighbors) throws NodeNotRegistered;

    void update(DeviceMap deviceMap, Set<Link> links) throws NodeNotRegistered;
}
