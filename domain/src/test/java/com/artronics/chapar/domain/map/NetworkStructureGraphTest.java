package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NetworkStructureGraphTest extends BaseNetStructureGraphTest{

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

    }

    @Test
    public void isIsolated_test_for_sensor() throws Exception {
        Sensor s = Sensor.create(UnicastAddress.create(client,12345L));
        networkStructure.addSensor(s);

        assertThat(networkStructure.isIsolated(s),is(true));

        assertThat(networkStructure.isIsolated(sensor30),is(false));
    }

}
