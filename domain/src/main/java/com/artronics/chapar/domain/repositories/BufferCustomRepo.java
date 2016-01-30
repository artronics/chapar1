package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.Buffer;

public interface BufferCustomRepo {
    Buffer persist(Buffer buffer, Long clientId);
}
