package com.artronics.chapar.core.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "devices")
public class Device extends AbstractBaseEntity{
    private final static Logger log = Logger.getLogger(Device.class);

    @Override
    public int hashCode()
    {
        //use getters for getting fields(for ORM) see this SO answer:
        //http://stackoverflow.com/questions/27581/what-issues-should-be-considered-when
        // -overriding-equals-and-hashcode-in-java

        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.id);

        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Device))
            return false;
        if (obj == this)
            return true;

        Device that = (Device) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(this.id,that.id);

        return eb.isEquals();
    }
}
