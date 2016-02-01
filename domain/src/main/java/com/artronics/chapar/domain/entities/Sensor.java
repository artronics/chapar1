package com.artronics.chapar.domain.entities;

import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.model.NetworkComponent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

import javax.persistence.*;

@Entity
@Table(name = "sensors")
public class Sensor extends AbstractBaseEntity implements NetworkComponent{
    private final static Logger log = Logger.getLogger(Sensor.class);

    private UnicastAddress address;

    public Sensor() {
    }

    private Sensor(UnicastAddress address) {
        this.address = address;
    }

    public static Sensor create(UnicastAddress address){
        return new Sensor(address);
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "add_id")
    public UnicastAddress getAddress() {
        return address;
    }

    public void setAddress(UnicastAddress address) {
        this.address = address;
    }

    @Override
    public int hashCode()
    {
        //use getters for getting fields(for ORM) see this SO answer:
        //http://stackoverflow.com/questions/27581/what-issues-should-be-considered-when
        // -overriding-equals-and-hashcode-in-java

        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.address);

        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Sensor))
            return false;
        if (obj == this)
            return true;

        Sensor that = (Sensor) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(this.address,that.address);

        return eb.isEquals();
    }

}
