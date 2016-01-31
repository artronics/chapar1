package com.artronics.chapar.controller.sdwn.packet;

import java.util.List;

public interface SdwnPacketConstants {
    Integer HEADER_LEN = 10;
    Integer DEF_NET_ID=1;
    Integer DEF_MAX_TTL=20;

    List<Integer> getContent();

    enum ByteIndex
    {
        LENGTH(0),
        NET_ID(1),
        SOURCE_H(2),
        SOURCE_L(3),
        DESTINATION_H(4),
        DESTINATION_L(5),
        TYPE(6),
        TTL(7),
        NEXT_HOP_H(8),
        NEXT_HOP_L(9),
        //        DISTANCE(10),
        BATTERY(11),
//        NEIGHBOUR(12),
//        START_TIME_H(11),
//        START_TIME_L(12),
//        STOP_TIME_H(13),
//        STOP_TIME_L(14),;
        ;
        private int value;

        ByteIndex(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }
}
