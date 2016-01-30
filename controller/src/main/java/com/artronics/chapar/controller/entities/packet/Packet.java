package com.artronics.chapar.controller.entities.packet;

import com.artronics.chapar.controller.entities.AbstractBaseEntity;
import com.artronics.chapar.domain.entities.Buffer;

import javax.persistence.*;

@Entity
@Table(name = "packets")
public class Packet extends AbstractBaseEntity {
    private Buffer buffer;

    public Packet() {
    }

    private Packet(Buffer buffer) {
        this.buffer = buffer;
    }

    public static Packet create(Buffer buffer){
        return new Packet(buffer);
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buff_id")
    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }
}
