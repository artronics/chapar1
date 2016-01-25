package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.BufferService;
import com.artronics.chapar.controller.services.PacketService;
import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.DeviceNotRegistered;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class BufferServiceImpl implements BufferService{
    private final static Logger log = Logger.getLogger(BufferServiceImpl.class);

    protected Map<Long,Device> registeredDevices;

    private PacketService packetService;

    @Override
    public void addBuffer(Long deviceId, Buffer buffer)
            throws MalformedPacketException, NodeNotRegistered {

        checkDevice(deviceId);

        Packet packet = new Packet(buffer.getContent());

        packetService.addPacket(packet, deviceId);
    }

    protected void checkDevice(Long deviceId){
        if (!registeredDevices.containsKey(deviceId))
            throw new DeviceNotRegistered();
    }

    @Autowired
    public void setPacketService(PacketService packetService) {
        this.packetService = packetService;
    }

    @Resource(name = "registeredDevices")
    public void setRegisteredDevices(Map<Long,Device> registeredDevices) {
        this.registeredDevices = registeredDevices;
    }

}
