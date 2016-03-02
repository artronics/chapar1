package com.artronics.chapar.domain.entities;

import org.apache.log4j.Logger;

import java.util.Comparator;

public class SensorComparator implements Comparator<Sensor>
{
    private final static Logger log = Logger.getLogger(SensorComparator.class);

    @Override
    public int compare(Sensor o1, Sensor o2)
    {
        if (o1.equals(o2))
            return 0;

        //if address is null node is greater. because we want to see it first
        if (o1.getAddress()==null)
            return 1;
        if (o2 == null) {
            return -1;
        }

        Long id1 = o1.getAddress().getClient()==null ? 0:o1.getAddress().getClient().getId();
        Long id2 = o2.getAddress().getClient()==null ? 0:o2.getAddress().getClient().getId();

        if (id1>id2)
            return 1;
        if (id1<id2)
            return -1;

        else {
            Long add1 = o1.getAddress().getLocalAddress();
            Long add2 = o2.getAddress().getLocalAddress();

            if ((o2.getType() == Sensor.Type.SINK) || add1>add2)
                return 1;

            if ((o1.getType() == Sensor.Type.SINK) || add1<add2)
                return -1;
        }

        return 0;
    }
}
