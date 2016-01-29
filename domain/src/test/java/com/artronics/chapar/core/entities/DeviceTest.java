package com.artronics.chapar.core.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class DeviceTest {

    Device aDevice;
    Device sameDevice;
    Device otherDevice;

    @Before
    public void setUp() throws Exception
    {
        aDevice = new Device();
        aDevice.setId(1L);

        sameDevice = new Device();
        sameDevice.setId(1L);

        otherDevice = new Device();
        otherDevice.setId(2L);
    }

    @Test
    public void test_equals_general(){
        assertTrue(aDevice.equals(aDevice));
        assertThat(aDevice,equalTo(sameDevice));
        assertThat(sameDevice,equalTo(aDevice));

        assertFalse(aDevice.equals(otherDevice));
        assertFalse(otherDevice.equals(aDevice));

        Device otherSameDevice = new Device();
        otherSameDevice.setId(aDevice.getId());
        assertThat(sameDevice,equalTo(otherSameDevice));

        Device nullDevice = null;
        assertFalse(aDevice.equals(nullDevice));
    }

    @Test
    public void test_hashCode(){
        assertThat(aDevice.hashCode(),equalTo(sameDevice.hashCode()));
    }

    @Test
    public void test_set(){
        Set<Device> devices = new HashSet<>();
        devices.add(aDevice);
        devices.add(sameDevice);

        Device otherSameDevice = new Device();
        otherSameDevice.setId(sameDevice.getId());

        assertThat(devices.size(),equalTo(1));
        assertTrue(devices.contains(otherSameDevice));

    }
}