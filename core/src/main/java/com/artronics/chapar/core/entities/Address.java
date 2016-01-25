package com.artronics.chapar.core.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Address extends AbstractBaseEntity{

    private Long localAdd;

    private Device device;

    public Address() {
    }

    public static Address create(Device device,Long localAdd){
        Address address = new Address();
        address.setDevice(device);
        address.setLocalAdd(localAdd);

        return address;
    }

    public static Address create(Long localAdd){
        return Address.create(null,localAdd);
    }

    public Long getLocalAdd() {
        return localAdd;
    }

    public void setLocalAdd(Long localAdd) {
        this.localAdd = localAdd;
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
        hcb.append(this.localAdd);
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
        eb.append(this.localAdd,that.localAdd);

        return eb.isEquals();
    }

    @Override
    public String toString() {
        String s = String.format("ADD:%-3d",this.getLocalAdd());

        return s;
    }
}
