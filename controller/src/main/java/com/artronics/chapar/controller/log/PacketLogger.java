package com.artronics.chapar.controller.log;

import com.artronics.chapar.controller.entities.packet.Packet;
import org.apache.log4j.Logger;

public interface PacketLogger<T extends Packet>
{
    Logger logBuffer = Logger.getLogger("com.artronics.chapar.logger.packet.buffer");
    Logger logDevice = Logger.getLogger("com.artronics.chapar.logger.packet.client");
    Logger logDeviceReport = Logger.getLogger("com.artronics.chapar.logger.packet.client.report");

    void log(T packet);

}
