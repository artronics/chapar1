package com.artronics.chapar.client.connection;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Device;

import java.io.IOException;

public interface ControllerResolver {
    Device connect(Device device, String url) throws IOException;

    void sendBuffer(Buffer buffer);

    void setDeviceId(Long deviceId);
}
