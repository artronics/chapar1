package com.artronics.chapar.core.entities;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class NodeLink implements Link{

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
        return null;
    }

    @Override
    public Double getWeight() {
        return null;
    }

    public void setDstNode(Node dstNode) {
        this.dstNode = dstNode;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

}
