package com.artronics.chapar.client.driver;


import com.artronics.chapar.client.exceptions.DeviceDriverException;

public interface DeviceDriver
{
    void init();

    void open() throws DeviceDriverException;

    void close() throws DeviceDriverException;
}
