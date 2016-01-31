package com.artronics.chapar.controller.entities.packet;

import com.artronics.chapar.domain.entities.Buffer;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class Packet <T extends Enum<T> & PacketType> {
    private Long id;

    protected Buffer buffer;

    protected T type;

    protected Date generatedAt;

    public Packet() {
    }

    public Packet(Buffer buffer) {
        this.buffer = buffer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buff_id")
    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Column(name = "type",nullable = false)
    public T getType() {
        return type;
    }

    public void setType(T type) {
        this.type = type;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "gen_at",columnDefinition = "DATETIME(6)")
    public Date getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Date generatedAt) {
        this.generatedAt = generatedAt;
    }

}
