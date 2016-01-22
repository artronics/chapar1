package com.artronics.chapar.core.map;

import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;
import org.apache.log4j.Logger;

import java.util.Set;

public class DeviceMapUpdaterImpl implements DeviceMapUpdater{
    private final static Logger log = Logger.getLogger(DeviceMapUpdaterImpl.class);

    @Override
    public void update(DeviceMap deviceMap, Set<Link> links) throws NodeNotRegistered {

    }

    @Override
    public void update(DeviceMap deviceMap, Node srcNode, Set<Node> newNeighbors) throws NodeNotRegistered {

    }

    private void checkIfNodesAreRegistered(DeviceMap deviceMap,Node srcNode,Set<Node> newNeighbors) throws NodeNotRegistered {
        if (!deviceMap.contains(srcNode))
            throw new NodeNotRegistered();

        if(newNeighbors.stream().anyMatch(node -> !deviceMap.contains(node)))
            throw new NodeNotRegistered();

    }
}
