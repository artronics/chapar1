package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.model.graph.GraphDelegator;
import com.artronics.chapar.domain.model.graph.impl.JGraphTDelegator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class NetworkStructureImplTest {

    private NetworkStructure networkStructure;
    private GraphDelegator graphDelegator;

    private Controller controller;
    private Client client;

    @Before
    public void setUp() throws Exception {
        graphDelegator = new JGraphTDelegator();
        networkStructure = new NetworkStructureImpl(graphDelegator);

        controller = new Controller(1L);
        client = new Client(10L,controller);
    }


    @Test
    public void it_should_addClient_and_return_true_if_asked_containsClient() throws Exception {
        Client client = new Client(1L);
        assertFalse(networkStructure.containsClient(client));

        networkStructure.addClient(client);

        assertThat(networkStructure.containsClient(client),is(true));
    }
}