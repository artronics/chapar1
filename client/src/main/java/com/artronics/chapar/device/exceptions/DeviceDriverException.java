package com.artronics.chapar.device.exceptions;

import org.apache.log4j.Logger;

public class DeviceDriverException extends RuntimeException{
    private final static Logger log = Logger.getLogger(DeviceDriverException.class);

    public DeviceDriverException() {
    }

    public DeviceDriverException(String message) {
        super(message);
    }

    public DeviceDriverException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceDriverException(Throwable cause) {
        super(cause);
    }

    public DeviceDriverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
