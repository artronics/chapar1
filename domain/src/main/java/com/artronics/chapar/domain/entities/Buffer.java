package com.artronics.chapar.domain.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Buffer {
    protected Long id;

    private List<Integer> content;

    private Direction direction;

    protected Date created;
    protected Date updated;

    public Buffer() {
    }

    public Buffer(List<Integer> content) {
        this.content = content;
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

    @ElementCollection
    @CollectionTable(name = "buff_content",joinColumns = @JoinColumn(name = "buff_id"))
    @Column(name = "content")
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

}
