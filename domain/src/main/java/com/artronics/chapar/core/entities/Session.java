package com.artronics.chapar.core.entities;

public final class Session {

    private Long deviceId;

    public Session() {
    }

    public Session(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
