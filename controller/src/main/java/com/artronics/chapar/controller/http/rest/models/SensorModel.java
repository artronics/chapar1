package com.artronics.chapar.controller.http.rest.models;

import org.apache.log4j.Logger;

import java.util.List;

public class SensorModel {
    private final static Logger log = Logger.getLogger(SensorModel.class);
    private long rid;
    private long localAdd;
    private List<SensorLinkModel> links;

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public long getLocalAdd() {
        return localAdd;
    }

    public void setLocalAdd(long localAdd) {
        this.localAdd = localAdd;
    }

    public List<SensorLinkModel> getLinks() {
        return links;
    }

    public void setLinks(List<SensorLinkModel> links) {
        this.links = links;
    }
}
