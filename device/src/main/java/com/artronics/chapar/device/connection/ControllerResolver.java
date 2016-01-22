package com.artronics.chapar.device.connection;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Device;

public interface ControllerResolver {
    Device connect(String url);

    void sendBuffer(Buffer buffer);
}
