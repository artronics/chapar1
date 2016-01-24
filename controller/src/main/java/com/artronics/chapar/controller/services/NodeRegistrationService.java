package com.artronics.chapar.controller.services;

import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;

public interface NodeRegistrationService {
    Node registerNode(Device device,Node node);
}
