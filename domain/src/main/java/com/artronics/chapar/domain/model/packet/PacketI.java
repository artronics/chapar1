package com.artronics.chapar.domain.model.packet;

public interface PacketI<T extends Enum<T> & PacketType>{
    T getType();

    void setType(T type);
}
