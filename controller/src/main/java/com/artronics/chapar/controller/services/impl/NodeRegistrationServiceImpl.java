package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.services.NodeRegistrationService;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class NodeRegistrationServiceImpl extends BaseService implements NodeRegistrationService{
    private final static Logger log = Logger.getLogger(NodeRegistrationServiceImpl.class);

    @Override
    public Node registerNode(Device device,@NotNull Node node) {
        checkDevice(device);

//        DeviceMap deviceMap = registeredDevices.get(device);
//        if (deviceMap.contains(node))
//            throw new NodeConflictException();
//
//        log.debug("Registering new "+node);
//        deviceMap.addNode(node);

        return node;
    }

}
