package com.artronics.chapar.client.driver;

import com.artronics.chapar.client.exceptions.DeviceDriverException;


/**
 *<tt>DeviceDriver</tt> is an abstraction layer which enables system to
 * communicate to a sensor.
 * <p>The implementation can use <code>connection_string</code> property as a way
 * to communicate to given device. If implementation needs authentication it should
 * be provided by user as environment variable because of security concerns.</p>
 * <p>You can inject <code>connection_string</code> in your implementation using
 * <em>Spring</em>'s <code>@Value</code> annotation</p>
 *
 * @author Seyed Jalal Hosseini (jalalhosseiny@gmail.com)
 */
public interface DeviceDriver
{
    /**
     * All the initializing process should take place here. This includes
     * listing available connections, filtering them or loading OS specific
     * low level device drivers. All the resources must be claimed here.
     */
    void init();

    /**
     * Calling this method will open a connection between device (sensor) and
     * <code>Client</code> application. Connection's name must be provided by user inside
     * configuration file or any other means which shall be documented accordingly.
     *
     * @throws DeviceDriverException the device driver exception
     */
    void open() throws DeviceDriverException;

    /**
     * Calling this method must free all resources taken by implementation. In order to
     * {@link #open()} the connection client should call {@link #init()} again to claim
     * require resources.
     *
     * @throws DeviceDriverException the device driver exception
     */
    void close() throws DeviceDriverException;
}
