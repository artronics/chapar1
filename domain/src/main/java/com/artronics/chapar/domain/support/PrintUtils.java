package com.artronics.chapar.domain.support;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

public class PrintUtils {
    private final static Logger log = Logger.getLogger(PrintUtils.class);

    public static String printShortNode(Sensor sensor)
    {
        String s = sensor.getType() == Sensor.Type.SINK ? "Sink-> " : "Sensor-> ";

        return String.format(s + "ADD:%-3d ", sensor.getAddress().getLocalAddress());
    }

    public static String printLongNode(Sensor sensor)
    {
        String s = printShortNode(sensor);
        String f = sensor.getId() == null ? "%-5s" : "%-5d";
        return String.format(s + "ID:" + f,
                sensor.getId() == null ? "UNK" : sensor.getId());

    }

    public static String printBuffer(List<Integer> content)
    {
        String s = "BUFF: ";
        for (Integer c : content) {
            s += String.format("%-4d", c);
        }

        return s;
    }

    public static String printNodeLinks(Sensor srcSensor, Set<SensorLink> links)
    {
//        String n = String.format("NEIGHBORS:  SRC: %s\n", printLongNode(srcSensor));
        String n = String.format(" |-%s \n", printLongNode(srcSensor));
        int nl = n.lastIndexOf("\n");
        int p = n.lastIndexOf("|-");
        int startP = p-nl;
        n += addSpace(p);
        n += "       \\   [WEIGHT]  [NEIGHBORS]\n";
        if (links.isEmpty()) {
            n += "             NO NEIGHBORS";
            return n;
        }

        nl = n.indexOf("\n");
        int q = n.indexOf("[WEIGHT]");
        for (SensorLink link : links) {
            n += addSpace(q-nl-4);
                n += String.format("|__[%-6.0f] ", link.getWeight());

            n += link.getDstSensor().toString();
            n += "\n";
        }

        return n;
    }

    public static String printNeighbors(Set<SensorLink> links, Integer i){
        String s="";
        if (i != null) {
            s+=addSpace(i);
        }
        s+="Neighbors:\n";
        int p =s.lastIndexOf("Neighbors:");
        s+=addSpace(p);

        i = s.lastIndexOf("\n");
        s += "   [WEIGHT]  [NEIGHBORS ]\n";
        p = s.lastIndexOf("[WEIGHT]")-i;

        for (SensorLink link : links) {
            s+=printOneNeighbor(link,p-1);
            s += "\n";
        }

        return s;
    }

    public static String printOneNeighbor(SensorLink link,Integer i){
        String s="";
        if (i != null) {
            s+=addSpace(i);
        }
        s += String.format("[%-6.0f] ", link.getWeight());

        s += " ["+link.getDstSensor().toString()+"]";

        return s;
    }

    public static String addSpace(int n)
    {
        String s = "";
        for (int i = 0; i < n; i++) {
            s += " ";
        }

        return s;
    }

}
