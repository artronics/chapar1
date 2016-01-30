package com.artronics.chapar.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "buffers")
public class Buffer {
    protected Long id;

    private List<Integer> content = new ArrayList<>();

    private Direction direction;

    private Client client;

    private Date receivedAt;
    private Date sentAt;

    protected Date created;
    protected Date updated;

    public Buffer() {
    }

    public Buffer(List<Integer> content) {
        this.content = content;
    }

    public Buffer(List<Integer> content, Direction direction) {
        this.content = content;
        this.direction = direction;
    }

    public Buffer(List<Integer> content, Direction direction, Client client) {
        this.content = content;
        this.direction = direction;
        this.client = client;
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

//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "buff_content",joinColumns = @JoinColumn(name = "id"))
//    @Column(name = "content")
    @Transient
    public List<Integer> getContent() {
        return content;
    }

    public void setContent(List<Integer> content) {
        this.content = content;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "received_at", columnDefinition = "DATETIME(6)")
    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent_at", columnDefinition = "DATETIME(6)")
    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Date getUpdated()
    {
        return updated;
    }

    public void setUpdated(Date updated)
    {
        this.updated = updated;
    }

    @PrePersist
    protected void onCreate()
    {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate()
    {
        updated = new Date();
    }

    public enum Direction{
        RX,
        TX
    }

    public static String soutBuffer(Buffer buffer){
        Direction dir = buffer.getDirection();
        String s = "BUFF:"+ dir +" ";

        if (dir==Direction.RX && buffer.getReceivedAt()!=null){
            s+="received at: "+ buffer.getReceivedAt();
        }
        if (dir==Direction.TX && buffer.getSentAt()!=null){
            s+= "sent at   : " +buffer.getSentAt();
        }

        for (Integer c : buffer.getContent()) {
            s += String.format("%-4d", c);
        }

        return s;
    }

}
