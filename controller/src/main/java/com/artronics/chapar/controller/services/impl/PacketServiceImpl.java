package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.factories.PacketFactory;
import com.artronics.chapar.controller.sdwn.packet.BaseSdwnPacket;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.controller.services.NetworkController;
import com.artronics.chapar.controller.services.NodeRegistrationService;
import com.artronics.chapar.controller.services.PacketService;
import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.entities.Packet;
import com.artronics.chapar.core.exceptions.DeviceNotRegistered;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class PacketServiceImpl implements PacketService{
    private final static Logger log = Logger.getLogger(PacketServiceImpl.class);

    private Map<Long,Device> registeredDevices;

    private PacketFactory packetFactory;

    private AddressRegistrationService addressRegistrationService;

    private NodeRegistrationService nodeRegistrationService;

    private NetworkController<SdwnPacketType,BaseSdwnPacket> networkController;

    @Override
    public void addPacket(Packet packet,Long deviceId) throws MalformedPacketException, NodeNotRegistered {
        checkDevice(deviceId);

        packet = packetFactory.create(packet);

        Device device = registeredDevices.get(deviceId);

        Address srcAddress = packet.getSrcAddress();
        srcAddress.setDevice(device);

        Address dstAddress = packet.getDstAddress();
        dstAddress.setDevice(device);

        addressRegistrationService.registerSrcDstAddressInPacket(srcAddress,dstAddress);

        Node srcNode = Node.create(srcAddress);
        Node dstNode = Node.create(dstAddress);
        nodeRegistrationService.registerSrcDstNodesInPacket(srcNode,dstNode);

        networkController.processPacket((BaseSdwnPacket) packet);
    }

    protected void checkDevice(Long deviceId){
        if (!registeredDevices.containsKey(deviceId))
            throw new DeviceNotRegistered();
    }

    @Autowired
    public void setNodeRegistrationService(NodeRegistrationService nodeRegistrationService) {
        this.nodeRegistrationService = nodeRegistrationService;
    }

    @Autowired
    @Qualifier("sdwnNetworkController")
    public void setNetworkController(NetworkController<SdwnPacketType,BaseSdwnPacket> networkController) {
        this.networkController = networkController;
    }

    @Autowired
    public void setAddressRegistrationService(AddressRegistrationService addressRegistrationService) {
        this.addressRegistrationService = addressRegistrationService;
    }

    @Resource(name = "registeredDevices")
    public void setRegisteredDevices(Map<Long,Device> registeredDevices) {
        this.registeredDevices = registeredDevices;
    }

    @Autowired
    public void setPacketFactory(PacketFactory packetFactory) {
        this.packetFactory = packetFactory;
    }

}
