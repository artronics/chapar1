package com.artronics.chapar.test.factory;

import com.artronics.chapar.controller.entities.packet.PacketUtils;
import com.artronics.chapar.controller.entities.packet.SdwnPacketType;
import com.artronics.chapar.core.entities.Buffer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BufferFactory {
    private final static Logger log = Logger.getLogger(BufferFactory.class);

    public static Buffer createReportBuffer(int src, int dst, Integer ... neighbors){
        List<Integer> b = new ArrayList<>(createHeader(src,dst, SdwnPacketType.REPORT));

        List<Integer> m = new ArrayList<>();
        for (int i = 0; i < neighbors.length; i++) {
            m.add(PacketUtils.getHighAddress(neighbors[i]));
            m.add(PacketUtils.getLowAddress(neighbors[i]));
            m.add(100);
        }
        m.add(0,0);//Distance
        m.add(1,255);//battery
        m.add(2,neighbors.length);

        b.addAll(m);

        b.set(0,b.size());//set final length

        return new Buffer(b);
    }

    public static List<Integer> createHeader(int src,int dst,SdwnPacketType type){
        List<Integer> h = new ArrayList<>(Arrays.asList(
                22,//length
                1,//Def net id
                PacketUtils.getHighAddress(src),//source H
                PacketUtils.getLowAddress(src),//source L
                PacketUtils.getHighAddress(dst),//destination H
                PacketUtils.getLowAddress(dst),//destination L
                type.getValue(),//type
                20,//TTL_MAX
                0,//next hop H
                0//next hop L
        ));

        return h;
    }
}
