package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.NodeRegistrationService;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.exceptions.DeviceNotRegistered;
import com.artronics.chapar.core.exceptions.NodeConflictException;
import com.artronics.chapar.core.map.DeviceMap;
import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NodeRegistrationServiceImpl implements NodeRegistrationService{
    private final static Logger log = Logger.getLogger(NodeRegistrationServiceImpl.class);

    private Map<Device,DeviceMap> registeredDevices;

    @Override
    public Node registerNode(Device device,@NotNull Node node) {
        if (!registeredDevices.containsKey(device))
            throw new DeviceNotRegistered();

        DeviceMap deviceMap = registeredDevices.get(device);
        if (deviceMap.contains(node))
            throw new NodeConflictException();

        log.debug("Registering new "+node);
        deviceMap.addNode(node);

        return node;
    }

    @Autowired
    public void setRegisteredDevices(Map<Device, DeviceMap> registeredDevices) {
        this.registeredDevices = registeredDevices;
    }

}
