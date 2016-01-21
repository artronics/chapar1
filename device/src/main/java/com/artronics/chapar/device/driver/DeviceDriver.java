package com.artronics.chapar.device.driver;


import com.artronics.chapar.device.exceptions.DeviceDriverException;

public interface DeviceDriver
{
    void init();

    void open() throws DeviceDriverException;

    void close() throws DeviceDriverException;
}
