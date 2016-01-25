package com.artronics.chapar.core.support;

import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.entities.NodeComparator;
import com.artronics.chapar.core.map.NodeMap;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

public class NodeMapPrinter {
    private final static Logger log = Logger.getLogger(NodeMapPrinter.class);

    public String printDeviceMap(NodeMap nodeMap, Device device){
        List<Node> nodes = nodeMap.getAllNodes();
        nodes.sort(new NodeComparator());

        String s = "\n";

        s+=getDeviceString(device);
        int p = s.indexOf(":");
        for (Node node : nodes) {
            Device dev = node.getAddress().getDevice();
            if (!dev.equals(device))
                continue;

            Set<Link> links = nodeMap.getLinks(node);
            s+=PrintUtils.addSpace(p);
            s+=PrintUtils.printNodeLinks(node,links);
            s+="\n";
        }

        return s;
    }


    private String getDeviceString(Device device) {
        String s = device == null ? "\nDevice: null" : device.toString();
        s += "\n    \\\n";
        return s;
    }
}
