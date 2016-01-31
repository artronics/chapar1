package com.artronics.chapar.domain.entities.address;

import com.artronics.chapar.domain.entities.Client;

import javax.persistence.*;

@Entity
@Table(name = "uni_addresses")
public class UnicastAddress extends Address{

    private Client client;

    @Override
    @AttributeOverride(name = "local_add",column = @Column(nullable = false))
    public Long getLocalAddress() {
        return super.getLocalAddress();
    }

    @OneToOne(fetch = FetchType.LAZY,targetEntity = Client.class)
    @JoinColumn(name = "client_id",nullable = false)
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
