package com.artronics.chapar.client.test;

import com.artronics.chapar.domain.entities.Client;

public interface ClientTestExecutor {
    void start();

    void setDelayBeforeStart(long delayBeforeStart);

    void setRegClient(Client regClient);
}
