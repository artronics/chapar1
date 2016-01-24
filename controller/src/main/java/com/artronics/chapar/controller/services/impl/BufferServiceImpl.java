package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.BufferService;
import com.artronics.chapar.controller.services.PacketService;
import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.DeviceNotRegistered;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import com.artronics.chapar.core.map.DeviceMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Map;

public class BufferServiceImpl implements BufferService{
    private final static Logger log = Logger.getLogger(BufferServiceImpl.class);

    private Map<Device,DeviceMap> registeredDevices;

    private PacketService packetService;

    @Override
    public void addBuffer(Long deviceId, Buffer buffer) throws MalformedPacketException {
        Device device = new Device(deviceId);
        if (!registeredDevices.containsKey(device))
            throw new DeviceNotRegistered();

        Packet packet = new Packet(buffer.getContent());

        packetService.addPacket(packet);
    }

    @Autowired
    public void setPacketService(PacketService packetService) {
        this.packetService = packetService;
    }

    @Resource(name = "registeredDevices")
    public void setRegisteredDevices(Map<Device, DeviceMap> registeredDevices) {
        this.registeredDevices = registeredDevices;
    }

}
