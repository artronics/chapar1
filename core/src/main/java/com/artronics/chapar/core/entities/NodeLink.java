package com.artronics.chapar.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class NodeLink extends AbstractBaseEntity implements Link{

    private Node dstNode;

    private Double weight;

    public NodeLink() {
    }

    public NodeLink(Node dstNode, Double weight) {
        this.dstNode = dstNode;
        this.weight = weight;
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
