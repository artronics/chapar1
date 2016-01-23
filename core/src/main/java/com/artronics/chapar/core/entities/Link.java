package com.artronics.chapar.core.entities;

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
}
