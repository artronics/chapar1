package com.artronics.chapar.domain.entities.address;

import javax.persistence.*;

@MappedSuperclass
public class Address {
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
