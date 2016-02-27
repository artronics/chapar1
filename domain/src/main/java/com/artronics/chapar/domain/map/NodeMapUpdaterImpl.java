package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.exceptions.SensorNotRegistered;
import com.artronics.chapar.domain.utilities.SensorLinkUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class NodeMapUpdaterImpl implements NodeMapUpdater {
    private final static Logger log = Logger.getLogger(NodeMapUpdaterImpl.class);

    private NodeMap nodeMap;

    @Override
    public void update(NodeMap nodeMap, Sensor srcSensor, Set<SensorLink> links, Set<Sensor> islandSensors) throws SensorNotRegistered {

        checkIfNodesAreRegistered(nodeMap, srcSensor,links);

        Set<SensorLink> oldLinks = nodeMap.getLinks(srcSensor);
        Set<SensorLink> mergedLinks = SensorLinkUtils.merge(oldLinks,links);

        Set<SensorLink> removedLinks = new HashSet<>(oldLinks);
        removedLinks.removeAll(links);

        mergedLinks.forEach(link -> {
            Sensor dstSensor = link.getDstSensor();
            if (removedLinks.contains(link)){
                nodeMap.removeLink(srcSensor, dstSensor);
                if (islandSensors !=null && nodeMap.isIsland(dstSensor)) {
                    islandSensors.add(dstSensor);
                }
            }
            else {
                nodeMap.addLink(srcSensor, dstSensor,link.getWeight());
            }
        });
    }

    @Override
    public void update(Sensor srcSensor, Set<SensorLink> links, Set<Sensor> islandSensors) throws SensorNotRegistered {
        if (nodeMap!=null)
            update(nodeMap, srcSensor,links, islandSensors);
    }

    @Override
    public void update(NodeMap nodeMap, Sensor srcSensor, Set<SensorLink> links) throws SensorNotRegistered {
        update(nodeMap, srcSensor,links,null);
    }

    private static void checkIfNodesAreRegistered(NodeMap nodeMap, Sensor srcSensor, Set<SensorLink> links) throws SensorNotRegistered {
        log.debug("Check if source node is registered.");
        if (!nodeMap.contains(srcSensor))
            throw new SensorNotRegistered();

        log.debug("Check if link's destination node is registered.");
        if (links.stream().anyMatch(link -> !nodeMap.contains(link.getDstSensor())))
            throw new SensorNotRegistered();
    }

    @Autowired
    public void setNodeMap(NodeMap nodeMap) {
        this.nodeMap = nodeMap;
    }
}
