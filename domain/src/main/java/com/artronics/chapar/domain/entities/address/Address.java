package com.artronics.chapar.domain.entities.address;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Address {
    private Long id;

    private Long localAddress;


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id",nullable = false,unique = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "local_add")
    public Long getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(Long localAddress) {
        this.localAddress = localAddress;
    }

    @Override
    public String toString() {
        return localAddress.toString();
    }
}
