package com.artronics.chapar.core.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Link {

    private Node dstNode;

    private Double weight;

    public Link(Node dstNode, Double weight) {
        this.dstNode = dstNode;
        this.weight = weight;
    }

    public Node getDstNode() {
        return dstNode;
    }

    public Double getWeight() {
        return weight;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.dstNode);

        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Link))
            return false;
        if (obj == this)
            return true;

        Link that = (Link) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(this.dstNode,that.dstNode);

        return eb.isEquals();
    }

}
