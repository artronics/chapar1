package com.artronics.chapar.controller.sdwn.helpers;

import com.artronics.chapar.controller.sdwn.packet.SdwnPacket;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketUtils;
import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeSdwnBufferFactory
{
    private final static Logger log = Logger.getLogger(FakeSdwnBufferFactory.class);
    private static final int HEADER_LEN = 10;

    public static List<Integer> createData(int src, int dst, int payloadLen)
    {
        List<Integer> buf = new ArrayList<>(createHeader(src, dst, payloadLen+HEADER_LEN, SdwnPacketType.DATA));
        buf.addAll(createPayload(payloadLen));

        return buf;
    }

    public static List<Integer> createABuffer(){
        List<Integer> buff = new ArrayList<>(createAHeader());
        buff.addAll(createPayload(10));

        return buff;
    }
    public static List<Integer> createPayload(int len){
        List<Integer> pl = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            pl.add(i);
        }

        return pl;
    }

    public static List<Integer> createHeader(int src, int dst, int len, SdwnPacketType type){
        return new ArrayList<>(Arrays.asList(
                len,
                SdwnPacket.DEF_NET_ID,
                SdwnPacketUtils.getHighAddress(src),
                SdwnPacketUtils.getLowAddress(src),
                SdwnPacketUtils.getHighAddress(dst),
                SdwnPacketUtils.getLowAddress(dst),
                type.getValue(),
                SdwnPacket.DEF_MAX_TTL,
                SdwnPacketUtils.getHighAddress(0),
                SdwnPacketUtils.getLowAddress(0)
        ));
    }
    public static List<Integer> createAHeader(){
        return createHeader(30, 0, 20, SdwnPacketType.DATA);
    }

    public static Buffer createReportBuffer(int src,int dst,Integer ... neighbors){
        List<Integer> b = new ArrayList<>(createHeader(src,dst,SdwnPacketType.REPORT));

        List<Integer> m = new ArrayList<>();
        for (int i = 0; i < neighbors.length; i++) {
            m.add(SdwnPacketUtils.getHighAddress(neighbors[i]));
            m.add(SdwnPacketUtils.getLowAddress(neighbors[i]));
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
                SdwnPacketUtils.getHighAddress(src),//source H
                SdwnPacketUtils.getLowAddress(src),//source L
                SdwnPacketUtils.getHighAddress(dst),//destination H
                SdwnPacketUtils.getLowAddress(dst),//destination L
                type.getValue(),//type
                20,//TTL_MAX
                0,//next hop H
                0//next hop L
        ));

        return h;
    }
}

