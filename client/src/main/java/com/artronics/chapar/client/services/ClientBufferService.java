package com.artronics.chapar.client.services;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;

public interface ClientBufferService {

    void start();

    void stop();

    Buffer sendBuffer(Buffer buffer);

    void setRegisteredClient(Client client);
}
