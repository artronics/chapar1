package com.artronics.chapar.client.repositories;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;

public interface ClientBufferCustomRepo{
    Buffer persist(Buffer buffer, Client client);
}
