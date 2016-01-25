package com.artronics.chapar.core.entities;

import org.apache.log4j.Logger;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node>
{
    private final static Logger log = Logger.getLogger(NodeComparator.class);

    @Override
    public int compare(Node o1, Node o2)
    {
        if (o1.equals(o2))
            return 0;

        //if address is null node is greater. because we want to see it first
        if (o1.getAddress()==null)
            return 1;
        if (o2 == null) {
            return -1;
        }

        Long id1 = o1.getAddress().getDevice()==null ? 0:o1.getAddress().getDevice().getId();
        Long id2 = o2.getAddress().getDevice()==null ? 0:o2.getAddress().getDevice().getId();

        if (id1>id2)
            return 1;
        if (id1<id2)
            return -1;

        else {
            Long add1 = o1.getAddress().getLocalAdd();
            Long add2 = o2.getAddress().getLocalAdd();

            if ((o2.getType() == Node.Type.SINK) || add1>add2)
                return 1;

            if ((o1.getType() == Node.Type.SINK) || add1<add2)
                return -1;
        }

        return 0;
    }
}
