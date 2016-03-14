package com.artronics.chapar.controller.http.rest.models;

import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import com.artronics.chapar.domain.entities.Buffer;
import org.apache.log4j.Logger;

import java.util.Date;

public class PacketModel {
    private final static Logger log = Logger.getLogger(PacketModel.class);
    private SdwnPacketType type;
    private long clientId;
    private Buffer.Direction dir;
    private Date sentAt;
    private Date receivedAt;
    private long srcAdd;
    private long dstAdd;

    public static Logger getLog() {
        return log;
    }

    public SdwnPacketType getType() {
        return type;
    }

    public void setType(SdwnPacketType type) {
        this.type = type;
    }

    public long getSrcAdd() {
        return srcAdd;
    }

    public void setSrcAdd(long srcAdd) {
        this.srcAdd = srcAdd;
    }

    public long getDstAdd() {
        return dstAdd;
    }

    public void setDstAdd(long dstAdd) {
        this.dstAdd = dstAdd;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public Buffer.Direction getDir() {
        return dir;
    }

    public void setDir(Buffer.Direction dir) {
        this.dir = dir;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }
}
