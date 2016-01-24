package com.artronics.chapar.controller.helpers;

import com.artronics.chapar.controller.sdwn.packet.PacketUtils;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacket;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeBufferFactory
{
    private final static Logger log = Logger.getLogger(FakeBufferFactory.class);
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
                PacketUtils.getHighAddress(src),
                PacketUtils.getLowAddress(src),
                PacketUtils.getHighAddress(dst),
                PacketUtils.getLowAddress(dst),
                type.getValue(),
                SdwnPacket.DEF_MAX_TTL,
                PacketUtils.getHighAddress(0),
                PacketUtils.getLowAddress(0)
        ));
    }
    public static List<Integer> createAHeader(){
        return createHeader(30, 0, 20, SdwnPacketType.DATA);
    }
}
