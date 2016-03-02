package com.artronics.chapar.controller.log;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.entities.packet.PacketType;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.address.Address;
import org.apache.log4j.Logger;

public class BasePacketLogger<T extends Enum<T> & PacketType> implements PacketLogger<T> {
    protected static final String BASE = "com.artronics.chapar.logger.packet";
    protected static Logger BUFF = Logger.getLogger(BASE + ".buffer");
    protected Class<?> clazz;

    public BasePacketLogger() {
    }

    public BasePacketLogger(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void log(Packet<T> packet) {
        PC_BASE_LG.debug(basePacketToString(packet));
    }

    protected String basePacketToString(Packet packet) {
        Address src = packet.getSrcAddress();
        Address dst = packet.getDstAddress();
        Client client = packet.getSrcAddress().getClient();

        String s ="Packet ";
        if (client != null)
            s = String.format("SRC: %s DST: %s from %s", src.toString(), dst.toString(), client.toString());

        return s;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
