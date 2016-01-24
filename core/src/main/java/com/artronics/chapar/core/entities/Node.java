package com.artronics.chapar.core.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "nodes")
public class Node extends AbstractBaseEntity {

    private Long address;

    //Normal as default value
    private Type type = Type.NORMAL;

    protected Status status = Status.UNREGISTERED;

    public Node() {
    }

    public Node(Long address) {
        this.address = address;
    }

    public Long getAddress() {
        return address;
    }

    public void setAddress(Long address) {
        this.address = address;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        if (!(obj instanceof Node))
            return false;
        if (obj == this)
            return true;

        Node that = (Node) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(this.address,that.address);

        return eb.isEquals();
    }

    public enum Type
    {
        SINK,
        NORMAL
    }

    public enum Status
    {
        UNREGISTERED,
        IDLE,
        ACTIVE,
        DISABLE, ISLAND,
    }

    @Override
    public String toString() {
        return printShortNode(this);
    }

    public static String printShortNode(Node node)
    {
        String s = node.getType() == Node.Type.SINK ? "Sink-> " : "Node-> ";

        return String.format(s + "ADD:%-3d ", node.getAddress());
    }
}
