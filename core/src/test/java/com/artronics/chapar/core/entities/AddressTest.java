package com.artronics.chapar.core.entities;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AddressTest {
    Device device;

    Address aAdd;
    Address sameAdd;
    Address anotherAdd;

    @Before
    public void setUp() throws Exception {
        device = new Device(87l);

        aAdd = Address.create(device,1L);
        sameAdd = Address.create(device,1L);
        anotherAdd = Address.create(device,2L);
    }

    @Test
    public void general_equality(){
        assertThat(aAdd,is(not(equalTo(null))));

        assertThat(aAdd,is(equalTo(aAdd)));

        assertThat(aAdd,is(equalTo(sameAdd)));
        assertThat(sameAdd,is(equalTo(aAdd)));
    }

    @Test
    public void two_add_with_diff_device_are_not_equal(){
        Device otherDevice = new Device(2323L);
        sameAdd.setDevice(otherDevice);

        assertThat(aAdd,is(not(equalTo(sameAdd))));
        assertThat(sameAdd,is(not(equalTo(aAdd))));
    }

    @Test
    public void test_hashCode(){
        assertTrue(aAdd.equals(sameAdd));

        assertThat(aAdd.hashCode(),is(equalTo(sameAdd.hashCode())));
    }

}