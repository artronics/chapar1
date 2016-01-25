package com.artronics.chapar.controller.sdwn.helpers;

import com.artronics.chapar.controller.sdwn.packet.PacketUtils;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacket;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.core.entities.Buffer;
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


    public static Buffer createReportBuffer(){
        Buffer b = new Buffer(Arrays.asList(

        ));
        Integer[] bytes = {
                22,//length
                1,//Def net id
                0,//source H
                30,//source L
                0,//destination H
                10,//destination L
                2,//type
                20,//TTL_MAX
                0,//next hop H
                0,//next hop L

                0,//distance
                255,//battery
                3,//neighbor

                0,// 1st addL
                39,// 1st addH
                196,// 1st rssi

                0,// 2nd addH
                50,// 2nd addL
                199,// 2nd rssi

                0,// 3rd addH
                40,// 3rd addL
                196,  // 3rd rssi
//                //a copy of 3rd to test hashset
//                0,// 3rd addH
//                30,// 3rd addL
//                100  // 3rd rssi (differ)
        };
        List<Integer> packetBytes = Arrays.asList(bytes);

        b.setContent(packetBytes);

        return b;
    }
}

