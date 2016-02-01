package com.artronics.chapar.domain.entities;

import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class SensorTest {

    Client client = new Client(100L);

    Sensor aSensor;
    Sensor sameSensor;
    Sensor otherSensor;

    @Before
    public void setUp() throws Exception
    {
        aSensor = Sensor.create(UnicastAddress.create(client,10L));
        sameSensor = Sensor.create(UnicastAddress.create(client,10L));
        otherSensor = Sensor.create(UnicastAddress.create(client,11L));
    }

    @Test
    public void test_equals_general(){
        assertTrue(aSensor.equals(aSensor));
        assertThat(aSensor,equalTo(sameSensor));
        assertThat(sameSensor,equalTo(aSensor));

        assertFalse(aSensor.equals(otherSensor));
        assertFalse(otherSensor.equals(aSensor));

        Sensor otherSameSensor = Sensor.create(aSensor.getAddress());
        assertThat(sameSensor,equalTo(otherSameSensor));

        Sensor nullSensor = null;
        assertFalse(aSensor.equals(nullSensor));
    }

    @Test
    public void two_nodes_with_diff_devices_are_not_eq(){
        aSensor.getAddress().setClient(new Client(244L));
        assertThat(aSensor,is(not(equalTo(sameSensor))));
    }
    @Test
    public void test_hashCode(){
        assertThat(aSensor.hashCode(),equalTo(sameSensor.hashCode()));
    }

    @Test
    public void test_set(){
        Set<Sensor> sensors = new HashSet<>();
        sensors.add(aSensor);
        sensors.add(sameSensor);

        Sensor otherSameSensor = Sensor.create(aSensor.getAddress());

        assertThat(sensors.size(),equalTo(1));
        assertTrue(sensors.contains(otherSameSensor));

    }

}