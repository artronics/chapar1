package com.artronics.chapar.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class NodeLink extends AbstractBaseEntity implements Link{

    private Node srcNode;

    private Node dstNode;

    private Double weight;

    public NodeLink() {
    }

    public NodeLink(Node srcNode, Node dstNode, Double weight) {
        this.srcNode = srcNode;
        this.dstNode = dstNode;
        this.weight = weight;
    }

    @ManyToOne
    public Node getSrcNode() {
        return srcNode;
    }

    public void setSrcNode(Node srcNode) {
        this.srcNode = srcNode;
        if (!srcNode.getLinks().contains(this)){
            srcNode.getLinks().add(this);
        }
    }

    @Override
    @Transient
    public Node getDstNode() {
        return dstNode;
    }

    @Override
    @Column(name = "weight")
    public Double getWeight() {
        return weight;
    }

    public void setDstNode(Node dstNode) {
        this.dstNode = dstNode;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

}
