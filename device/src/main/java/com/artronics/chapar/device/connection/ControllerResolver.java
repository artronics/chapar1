package com.artronics.chapar.device.connection;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Device;

import java.io.IOException;

public interface ControllerResolver {
    Device connect(Device device, String url) throws IOException;

    void sendBuffer(Buffer buffer);

    void setDeviceId(Long deviceId);
}
