package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.model.graph.GraphDelegator;
import com.artronics.chapar.domain.model.graph.impl.JGraphTDelegator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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
    public void it_should_add_controller() throws Exception {
        networkStructure.addController(controller);

        assertThat(networkStructure.getController(),is(equalTo(controller)));
    }

    @Test(expected = IllegalStateException.class)
    public void it_should_throw_exp_if_we_add_another_or_same_controller() throws Exception {
        networkStructure.addController(controller);
        networkStructure.addController(controller);
    }

}