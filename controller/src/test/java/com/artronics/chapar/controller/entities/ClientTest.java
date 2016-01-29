package com.artronics.chapar.controller.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ClientTest {

    Client aClient;
    Client sameClient;
    Client otherClient;

    @Before
    public void setUp() throws Exception
    {
        aClient = new Client();
        aClient.setId(1L);

        sameClient = new Client();
        sameClient.setId(1L);

        otherClient = new Client();
        otherClient.setId(2L);
    }

    @Test
    public void test_equals_general(){
        assertTrue(aClient.equals(aClient));
        assertThat(aClient,equalTo(sameClient));
        assertThat(sameClient,equalTo(aClient));

        assertFalse(aClient.equals(otherClient));
        assertFalse(otherClient.equals(aClient));

        Client otherSameClient = new Client();
        otherSameClient.setId(aClient.getId());
        assertThat(sameClient,equalTo(otherSameClient));

        Client nullClient = null;
        assertFalse(aClient.equals(nullClient));
    }

    @Test
    public void test_hashCode(){
        assertThat(aClient.hashCode(),equalTo(sameClient.hashCode()));
    }

    @Test
    public void test_set(){
        Set<Client> clients = new HashSet<>();
        clients.add(aClient);
        clients.add(sameClient);

        Client otherSameClient = new Client();
        otherSameClient.setId(sameClient.getId());

        assertThat(clients.size(),equalTo(1));
        assertTrue(clients.contains(otherSameClient));

    }
}