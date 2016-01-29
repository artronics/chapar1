package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Node;

import java.util.Set;

public interface DeviceMapUpdater {
    void update(DeviceMap deviceMap, Node srcNode, Set<Node> newNeighbors);
}
