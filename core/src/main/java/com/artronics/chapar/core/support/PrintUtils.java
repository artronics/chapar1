package com.artronics.chapar.core.support;

import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

public class PrintUtils {
    private final static Logger log = Logger.getLogger(PrintUtils.class);

    public static String printShortNode(Node node)
    {
        String s = node.getType() == Node.Type.SINK ? "Sink-> " : "Node-> ";

        return String.format(s + "ADD:%-3d ", node.getAddress().getLocalAdd());
    }

    public static String printLongNode(Node node)
    {
        String s = printShortNode(node);
        String f = node.getId() == null ? "%-5s" : "%-5d";
        return String.format(s + "[%-7s] ID:" + f, node.getStatus(),
                node.getId() == null ? "UNK" : node.getId());

    }

    public static String printBuffer(List<Integer> content)
    {
        String s = "BUFF: ";
        for (Integer c : content) {
            s += String.format("%-4d", c);
        }

        return s;
    }

    public static String printNodeLinks(Node srcNode,Set<Link> links)
    {
//        String n = String.format("NEIGHBORS:  SRC: %s\n", printLongNode(srcNode));
        String n = String.format("%s \n", printLongNode(srcNode));
        int p = n.indexOf(" ");
        n += addSpace(p);
        n += "|   [WEIGHT]  [NEIGHBORS]\n";
        if (links.isEmpty()) {
            n += "NO NEIGHBORS";
            return n;
        }

        int q = n.indexOf("|");
        for (Link link : links) {
            n += addSpace(p);
                n += String.format("|__[%-6.0f] ", link.getWeight());

            n += link.getDstNode().toString();
            n += "\n";
        }

        return n;
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
