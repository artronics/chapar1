package com.artronics.chapar.core.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nodes")
public class Node extends AbstractBaseEntity {

    private Long address;

    private List<NodeLink> links = new ArrayList<>();

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

//    @ElementCollection(fetch = FetchType.EAGER)
////    @CollectionType(type = "java.util.ArrayList")
//    @CollectionTable(name = "neighbors", joinColumns = @JoinColumn(name = "id"))
//    @Column(name = "neighbor")
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    @JoinColumn(name = "links",referencedColumnName = "id")
    @JoinTable(
            name = "node_links",joinColumns = {@JoinColumn(name = "node_id",referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "id")
    )
    public List<NodeLink> getLinks() {
        return links;
    }

    public void setLinks(List<NodeLink> links) {
        this.links = links;
    }

    public void addLink(NodeLink link){
        this.links.add(link);

        if (link.getSrcNode()!=this) {
            link.setSrcNode(this);
        }
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

}
