package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.NodeRegistrationService;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.map.DeviceMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NodeRegistrationServiceImpl implements NodeRegistrationService{
    private final static Logger log = Logger.getLogger(NodeRegistrationServiceImpl.class);

    private Map<Device,DeviceMap> registeredDevices;

    @Override
    public Node registerNode(Device device,Node node) {
        DeviceMap deviceMap = registeredDevices.get(device);
        deviceMap.addNode(node);

        return node;
    }

    @Autowired
    public void setRegisteredDevices(Map<Device, DeviceMap> registeredDevices) {
        this.registeredDevices = registeredDevices;
    }

}
