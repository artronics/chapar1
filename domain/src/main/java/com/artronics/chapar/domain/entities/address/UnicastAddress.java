package com.artronics.chapar.domain.entities.address;

import com.artronics.chapar.domain.entities.Client;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "uni_addresses")
public class UnicastAddress extends Address{

    private Client client;

    public static UnicastAddress create(Client client, Long localAdd){
        UnicastAddress ua = new UnicastAddress();
        ua.setClient(client);
        ua.setLocalAddress(localAdd);

        return ua;
    }

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

    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.getLocalAddress());
        hcb.append(this.client);

        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof UnicastAddress))
            return false;
        if (obj == this)
            return true;

        UnicastAddress that = (UnicastAddress) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(this.getClient(),that.getClient());
        eb.append(this.getLocalAddress(),that.getLocalAddress());

        return eb.isEquals();
    }

}
