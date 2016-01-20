package com.artronics.chapar.core.remote;

import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Session;
import com.artronics.chapar.core.exceptions.ControllerConnectionException;

public interface RemoteControllerService extends RemoteService{

    Session connect(String controllerUrl) throws ControllerConnectionException;

    Device registerDevice(Device device);

}
