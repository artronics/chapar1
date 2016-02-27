package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.exceptions.SensorNotRegistered;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NodeMapBasicTest {

    private NodeMapUpdaterImpl mapUpdater;

    private NodeMap nodeMap;

    Client client = new Client(234L);

    private Sensor sink;
    private Sensor sensor0;
    private Sensor sensor1;
    private Sensor sensor2;

    @Before
    public void setUp() throws Exception {
        mapUpdater = new NodeMapUpdaterImpl();

        nodeMap = new NodeMapImpl();
        mapUpdater.setNodeMap(nodeMap);

        sink = Sensor.create(UnicastAddress.create(client,10L));
        sink.setType(Sensor.Type.SINK);
        sensor0 = Sensor.create(UnicastAddress.create(client,0L));
        sensor1 = Sensor.create(UnicastAddress.create(client,1L));
        sensor2 = Sensor.create(UnicastAddress.create(client,2L));

        //These nodes are registered but there is no any link between them
        nodeMap.addNode(sink);
        nodeMap.addNode(sensor0);
        nodeMap.addNode(sensor1);
        nodeMap.addNode(sensor2);
    }
    /*
        General behaviour
     */
    @Test
    public void it_should_create_new_links() throws SensorNotRegistered {
        SensorLink l0_1 = new SensorLink(sensor1,23D);
        SensorLink l0_2 = new SensorLink(sensor2,22D);
        Set<SensorLink> links = new HashSet<>(Arrays.asList(l0_1,l0_2));
        mapUpdater.update(nodeMap, sensor0,links);

        assertThat(nodeMap.hasLink(sensor0, sensor1),is(true));
        assertThat(nodeMap.hasLink(sensor0, sensor2),is(true));

        assertThat(nodeMap.hasLink(sensor1, sensor0),is(true));
        assertThat(nodeMap.hasLink(sensor2, sensor0),is(true));
    }

    @Test
    public void it_should_should_keep_state_of_previous_map() throws Exception {
        SensorLink l0_1 = new SensorLink(sensor1,23D);
        SensorLink l0_2 = new SensorLink(sensor2,22D);
        Set<SensorLink> links = new HashSet<>(Arrays.asList(l0_1,l0_2));
        mapUpdater.update(nodeMap, sensor0,links);

        SensorLink l2_0 = new SensorLink(sensor0,28D);
        SensorLink l2_s = new SensorLink(sink,74D);
        links.clear();
        links.add(l2_0);
        links.add(l2_s);
        mapUpdater.update(nodeMap, sensor2,links);

        assertThat(nodeMap.hasLink(sensor1, sensor0),is(true));
        assertThat(nodeMap.hasLink(sensor0, sensor2),is(true));

        //form first report
        assertThat(nodeMap.hasLink(sensor0, sensor1),is(true));
        assertThat(nodeMap.hasLink(sensor0, sensor2),is(true));
    }

}
