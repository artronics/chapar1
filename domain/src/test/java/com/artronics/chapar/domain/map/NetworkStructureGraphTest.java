package com.artronics.chapar.domain.map;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

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

    @Test
    public void It_should_return_true_if_a_node_hasLink_to_other_node()
    {
        assertTrue(networkStructure.hasLink(sensor135, sensor136));
        assertTrue(networkStructure.hasLink(sensor136, sensor135));

        assertFalse(networkStructure.hasLink(sensor135, sensor137));
    }

    @Test
    public void It_should_return_false_if_we_ask_a_node_hasLink_to_itself()
    {
        assertThat(networkStructure.hasLink(sensor135, sensor135), equalTo(false));
    }

    @Test
    public void it_should_give_all_links_to_a_node(){
        Set<SensorLink> links = networkStructure.getLinks(sensor135);
//        assertThat(links.size(),is(equalTo(3)));
    }

}
