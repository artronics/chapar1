package com.artronics.chapar.controller.http.rest.models;

import org.apache.log4j.Logger;

public class SensorLinkModel {
    private final static Logger log = Logger.getLogger(SensorLinkModel.class);
    private long dstAdd;
    private double weight;

    public long getDstAdd() {
        return dstAdd;
    }

    public void setDstAdd(long dstAdd) {
        this.dstAdd = dstAdd;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
