package com.artronics.chapar.controller.services;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import com.artronics.chapar.core.exceptions.NodeNotRegistered;

public interface BufferService {
    void addBuffer(Long deviceId, Buffer buffer) throws MalformedPacketException, NodeNotRegistered;
}
