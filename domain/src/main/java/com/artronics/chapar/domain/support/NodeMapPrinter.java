package com.artronics.chapar.domain.support;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorComparator;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.map.NodeMap;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

public class NodeMapPrinter {
    private final static Logger log = Logger.getLogger(NodeMapPrinter.class);

    public String printDeviceMap(NodeMap nodeMap, Client client){
        List<Sensor> sensors = nodeMap.getAllNodes();
        sensors.sort(new SensorComparator());

        String s = "\n";

        s+=getDeviceString(client);
        int nl = s.indexOf("\n");
        int p = s.lastIndexOf("\\");
        for (Sensor sensor : sensors) {
            Client dev = sensor.getAddress().getClient();
            if (!dev.equals(client))
                continue;

            s+="     "+ PrintUtils.printLongNode(sensor);
            Set<SensorLink> links = nodeMap.getLinks(sensor);
            s+="\n";
//            p = s.lastIndexOf(p-nl+3);
            s+=PrintUtils.printNeighbors(links,10);
            s+="\n";
        }

        return s;
    }


    private String getDeviceString(Client client) {
        String s = client == null ? "\nClient: null" : client.toString();
        s += "\n    \\\n";
        return s;
    }
}
