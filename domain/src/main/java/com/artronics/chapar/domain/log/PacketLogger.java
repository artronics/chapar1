package com.artronics.chapar.domain.log;

import com.artronics.chapar.domain.model.packet.Packet;
import org.apache.log4j.Logger;

public interface PacketLogger<T extends Packet>
{
    Logger logBuffer = Logger.getLogger("com.artronics.chapar.logger.packet.buffer");
    Logger logDevice = Logger.getLogger("com.artronics.chapar.logger.packet.client");
    Logger logDeviceReport = Logger.getLogger("com.artronics.chapar.logger.packet.client.report");

    void log(T packet);

}