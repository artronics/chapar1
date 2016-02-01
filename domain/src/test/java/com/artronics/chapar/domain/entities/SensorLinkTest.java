package com.artronics.chapar.domain.entities;

import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class SensorLinkTest {
    private static final boolean HAPPENED = true;

    private boolean change;

    Client client = new Client(100L);
    Sensor aSensor;
    Sensor sameSensor;
    Sensor otherSensor;

    SensorLink aSensorLink;
    SensorLink sameSensorLink;
    SensorLink sameSensorLinkWithDiffWeight;
    SensorLink otherSensorLink;

    @Before
    public void setUp() throws Exception {
        aSensor = Sensor.create(UnicastAddress.create(client,1L));
        sameSensor = Sensor.create(UnicastAddress.create(client,1L));
        otherSensor = Sensor.create(UnicastAddress.create(client,3L));

        aSensorLink = new SensorLink(aSensor,10D);
        sameSensorLink = new SensorLink(sameSensor,10D);
        sameSensorLinkWithDiffWeight= new SensorLink(sameSensor,423D);
        otherSensorLink = new SensorLink(otherSensor,233D);
    }

    @Test
    public void equal_general_test(){
        assertThat(aSensorLink,is(equalTo(aSensorLink)));

        assertThat(aSensorLink,is(equalTo(sameSensorLink)));
        assertThat(sameSensorLink,is(equalTo(aSensorLink)));

        assertThat(aSensorLink,is(not(equalTo(otherSensorLink))));
    }

    @Test
    public void for_equality_weight_is_not_important(){

        assertThat(aSensorLink,is(equalTo(sameSensorLinkWithDiffWeight)));

        assertThat(sameSensorLinkWithDiffWeight,is(equalTo(aSensorLink)));
    }

    @Test
    public void if_two_links_are_equal_hash_must_be_equal(){
        assertThat(aSensorLink.hashCode(),is(equalTo(sameSensorLink.hashCode())));
    }

    @Test
    public void two_equal_links_with_diff_weight_should_return_same_hash(){
        assertThat(aSensorLink.hashCode(),is(equalTo(sameSensorLinkWithDiffWeight.hashCode())));
    }

    @Test
    public void test_in_HashSet(){
        Set<SensorLink> links = new HashSet<>();
        links.add(aSensorLink);
        links.add(sameSensorLink);

        assertThat(links.size(),is(equalTo(1)));

        links.add(sameSensorLinkWithDiffWeight);
        assertThat(links.size(),is(equalTo(1)));

        links.add(otherSensorLink);
        assertThat(links.size(),is(equalTo(2)));
    }

    @Ignore("experimental against JDK.")
    @Test
    public void it_should_update_set_if_we_change_weight(){
        Set<SensorLink> links = new HashSet<>();
        links.add(aSensorLink);

        aSensorLink.setWeight(22D);

        change = links.add(new SensorLink(aSensor,3232D));

        assertThat(change,is(HAPPENED));
    }

}