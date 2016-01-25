package com.artronics.chapar.controller.sdwn.log;

import com.artronics.chapar.controller.sdwn.packet.ReportPacket;
import com.artronics.chapar.core.log.BasePacketLogger;
import com.artronics.chapar.core.support.PrintUtils;
import org.apache.log4j.Logger;

public class SdwnPacketLogger extends BasePacketLogger {
    private final static Logger log = Logger.getLogger(SdwnPacketLogger.class);
    private static Logger REPORT = Logger.getLogger(BASE + ".report");

    public SdwnPacketLogger(Class<?> clazz) {
        super(clazz);
        REPORT = Logger.getLogger(BASE + ".report" + clazz.getSimpleName());
    }


    public void logReport(ReportPacket packet) {
        BUFF.debug(PrintUtils.printBuffer(packet.getContent()));

    }

//    private String logReport(ReportPacket packet) {
////        return PrintUtils.printNodeLinks();
//        return null;
//    }


}
