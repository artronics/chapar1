package com.artronics.chapar.domain.entities;

import com.artronics.chapar.domain.model.NetworkComponent;

import javax.persistence.*;

@Entity
@Table(name = "controllers")
public class Controller implements NetworkComponent{
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
