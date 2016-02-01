package com.artronics.chapar.domain.model.graph.impl;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.model.graph.GraphDelegator;
import com.artronics.chapar.domain.model.graph.UndirectedWeightedGraph;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JGraphTDelegatorTest {
    private GraphDelegator graphDelegator;

    private Sensor s1;
    private Sensor s2;
    @Before
    public void setUp() throws Exception {
        graphDelegator = new JGraphTDelegator();

        Client client = new Client(new Controller());
        s1 = new Sensor(UnicastAddress.create(client,10L));
        s2 = new Sensor(UnicastAddress.create(client,20L));
    }

    @Test
    public void it_should_create_undirectedWeightedGraph() throws Exception {
        UndirectedWeightedGraph<Sensor,SensorLink> graph = graphDelegator.createUndirectedWeightedGraph(Sensor.class,SensorLink.class);

        graph.addVertex(s1);
        assertThat(graph.containsVertex(s1),is(true));
    }
}