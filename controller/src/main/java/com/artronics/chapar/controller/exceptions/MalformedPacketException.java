package com.artronics.chapar.controller.exceptions;

import org.apache.log4j.Logger;

public class MalformedPacketException extends Exception {
    private final static Logger log = Logger.getLogger(MalformedPacketException.class);

    public MalformedPacketException() {
    }

    public MalformedPacketException(String message) {
        super(message);
    }

    public MalformedPacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public MalformedPacketException(Throwable cause) {
        super(cause);
    }

    public MalformedPacketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
