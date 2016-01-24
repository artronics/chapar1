package com.artronics.chapar.core.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Address extends AbstractBaseEntity{

    private Long address;

    private Device device;

    public Address() {
    }

    public Address(Device device) {
        this.device = device;
    }

    public Long getAddress() {
        return address;
    }

    public void setAddress(Long address) {
        this.address = address;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.address);
        hcb.append(this.device);

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

        eb.append(this.device,that.device);
        eb.append(this.address,that.address);

        return eb.isEquals();
    }
}
