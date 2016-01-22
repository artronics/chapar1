package com.artronics.chapar.core.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "links")
public class NodeLink implements Link{

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

    @Override
    public Node getSrcNode() {
        return null;
    }

    @Override
    public Node getDstNode() {
        return null;
    }

    @Override
    public Double getWeight() {
        return null;
    }

    public void setSrcNode(Node srcNode) {
        this.srcNode = srcNode;
    }

    public void setDstNode(Node dstNode) {
        this.dstNode = dstNode;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

}
