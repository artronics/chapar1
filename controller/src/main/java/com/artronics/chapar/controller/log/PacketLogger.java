package com.artronics.chapar.controller.log;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.entities.packet.PacketType;
import org.apache.log4j.Logger;

public interface PacketLogger<T extends Enum<T> & PacketType>
{
    Logger BF_LG = Logger.getLogger("com.artronics.chapar.logger.packet.buffer");
    Logger PC_BASE_LG = Logger.getLogger("com.artronics.chapar.logger.packet.base");

    Logger logBuffer = Logger.getLogger("com.artronics.chapar.logger.packet.buffer");
    Logger logDevice = Logger.getLogger("com.artronics.chapar.logger.packet.client");
    Logger logDeviceReport = Logger.getLogger("com.artronics.chapar.logger.packet.client.report");

    void log(Packet<T> packet);

}
