package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

public class NetworkStructureImplTest {
    private NetworkStructureImpl networkStructure;

    private Map<Sensor,Sensor> registeredSensors;

    private Controller controller;
    private Client client;
    private Sensor sensor;

    @Before
    public void setUp() throws Exception {
        registeredSensors = new HashMap<>();

        networkStructure = new NetworkStructureImpl();
        networkStructure.setRegisteredSensors(registeredSensors);
        networkStructure.setNodeMap(new NodeMapImpl());

        controller = new Controller(1L);
        client = new Client(10L,controller);

        sensor = Sensor.create(UnicastAddress.create(client,1000L));
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

    @Test
    public void it_should_add_sensor_to_registeredSensors() throws Exception {
        networkStructure.addSensor(sensor);

        assertThat(registeredSensors.containsKey(sensor),is(true));
    }

    @Test
    public void it_should_add_sensor_to_graph() throws Exception {
        networkStructure.addSensor(sensor);

        assertThat(networkStructure.containsSensor(sensor),is(true));
    }

    @Test
    public void it_should_get_registered_sensor() throws Exception {
        networkStructure.addSensor(sensor);

        assertThat(networkStructure.getSensor(sensor),is(equalTo(sensor)));
    }

    @Test
    public void it_should_update_registeredSensors_with_new_one() throws Exception {
        networkStructure.addSensor(sensor);

        Sensor newSensor = Sensor.create(UnicastAddress.create(client,1000L));

        networkStructure.addSensor(newSensor);
        assertSame(networkStructure.getSensor(sensor),newSensor);
    }

}