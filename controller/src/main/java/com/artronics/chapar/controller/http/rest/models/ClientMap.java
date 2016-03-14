package com.artronics.chapar.controller.http.rest.models;

import com.artronics.chapar.domain.entities.Sensor;
import org.apache.log4j.Logger;

import java.util.List;

public class ClientMap {
    private final static Logger log = Logger.getLogger(ClientMap.class);
    private long rid;
    private List<SensorModel> sensors;

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public List<SensorModel> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorModel> sensors) {
        this.sensors = sensors;
    }
}
