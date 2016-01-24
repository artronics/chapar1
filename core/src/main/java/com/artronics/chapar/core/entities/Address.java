package com.artronics.chapar.core.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Address {
    private Long id;

    private Address(Long id) {
        this.id = id;
    }

    public static Address create(Long id){
        return new Address(id);
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.id);

        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Address))
            return false;
        if (obj == this)
            return true;

        Address that = (Address) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(this.id,that.id);

        return eb.isEquals();
    }
}
