package com.artronics.chapar.controller.sdwn.map;

import com.artronics.chapar.controller.sdwn.packet.PacketUtils;
import com.artronics.chapar.controller.sdwn.packet.ReportPacket;
import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;
import com.artronics.chapar.core.map.NodeMapUpdater;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SdwnNodeMapUpdater {
    private final static Logger log = Logger.getLogger(SdwnNodeMapUpdater.class);

    private WeightCalculator weightCalculator;

    private NodeMapUpdater nodeMapUpdater;

    public Set<Link> createLinks(ReportPacket packet){
        List<Integer> content = packet.getContent();
        Device device = packet.getSrcAddress().getDevice();

        Set<Link> links = new HashSet<>();

        for (int i = ReportPacket.NEIGHBOR_INDEX; i < content.size(); i += 3) {
            Integer add = PacketUtils.joinAddresses(content.get(i),
                    content.get(i + 1));
            int rssi = content.get(i + 2);

            Double weight = weightCalculator.calculate(rssi);

            Node node = Node.create(Address.create(device,add.longValue()));
            Link neighbor = new Link(node,weight);
            links.add(neighbor);
        }

        return links;
    }

    public void updateMap(Node srcNode,Set<Link> links,Set<Node> islandedNode) throws NodeNotRegistered {
        nodeMapUpdater.update(srcNode,links,islandedNode);
    }

    @Autowired
    public void setNodeMapUpdater(NodeMapUpdater nodeMapUpdater) {
        this.nodeMapUpdater = nodeMapUpdater;
    }

    @Autowired
    public void setWeightCalculator(WeightCalculator weightCalculator) {
        this.weightCalculator = weightCalculator;
    }

}
