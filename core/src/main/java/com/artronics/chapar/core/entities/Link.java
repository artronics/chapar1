package com.artronics.chapar.core.entities;

public interface Link {
    Node getSrcNode();

    Node getDstNode();

    Double getWeight();
}
