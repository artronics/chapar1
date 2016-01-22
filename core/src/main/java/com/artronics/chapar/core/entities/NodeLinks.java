package com.artronics.chapar.core.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "links")
public class NodeLinks extends AbstractBaseEntity {

    private Node srcNode;

    private List<NodeLink> nodeLinks;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "src_node", nullable = false)
    public Node getSrcNode() {
        return srcNode;
    }

    public void setSrcNode(Node srcNode) {
        this.srcNode = srcNode;
    }

    //    @OneToMany
//    @JoinColumn()
    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionType(type = "java.util.ArrayList")
    @CollectionTable(name = "links_collections", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "content")
    public List<NodeLink> getNodeLinks() {
        return nodeLinks;
    }

    public void setNodeLinks(List<NodeLink> nodeLinks) {
        this.nodeLinks = nodeLinks;
    }

}
