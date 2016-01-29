package com.artronics.chapar.controller.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class SensorTest {

    Sensor aSensor = new Sensor(10L);
    Sensor sameSensor = new Sensor(10L);
    Sensor otherSensor = new Sensor(11L);

    @Before
    public void setUp() throws Exception
    {
    }

    @Test
    public void test_equals_general(){
        assertTrue(aSensor.equals(aSensor));
        assertThat(aSensor,equalTo(sameSensor));
        assertThat(sameSensor,equalTo(aSensor));

        assertFalse(aSensor.equals(otherSensor));
        assertFalse(otherSensor.equals(aSensor));

        Sensor otherSameSensor = new Sensor(aSensor.getAddress());
        assertThat(sameSensor,equalTo(otherSameSensor));

        Sensor nullSensor = null;
        assertFalse(aSensor.equals(nullSensor));
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

        Sensor otherSameSensor = new Sensor(aSensor.getAddress());

        assertThat(sensors.size(),equalTo(1));
        assertTrue(sensors.contains(otherSameSensor));

    }

}