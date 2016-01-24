package com.artronics.chapar.core.log;

import com.artronics.chapar.core.entities.Packet;
import org.apache.log4j.Logger;

public interface PacketLogger
{
    Logger logBuffer = Logger.getLogger("com.artronics.chapar.logger.packet.buffer");
    Logger logDevice = Logger.getLogger("com.artronics.chapar.logger.packet.base");
    Logger logDeviceReport = Logger.getLogger("com.artronics.chapar.logger.packet.device.report");

    void log(Packet packet);

}
