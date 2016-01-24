package com.artronics.chapar.core.log;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.support.PrintUtils;
import org.apache.log4j.Logger;

public class BasePacketLogger implements PacketLogger{
    private static final String BASE = "com.artronics.chapar.logger.packet";
    private static Logger BUFF = Logger.getLogger(BASE+".buffer");
    private Class<?> clazz;

    public BasePacketLogger()
    {
    }

    public BasePacketLogger(Class<?> clazz)
    {
        this.clazz = clazz;
    }

    public void logBuffer(Buffer buffer){
        BUFF.debug(PrintUtils.printBuffer(buffer.getContent()));
    }

    @Override
    public void log(Packet packet)
    {

    }
}
