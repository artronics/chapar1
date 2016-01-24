package com.artronics.chapar.core.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Set;

public class Address extends AbstractBaseEntity{

    private Set<Node> nodes;

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
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
