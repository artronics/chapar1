package com.artronics.chapar.device.connection;

import com.artronics.chapar.core.entities.Session;

public interface ControllerResolver {
    Session connect(String url);
}
