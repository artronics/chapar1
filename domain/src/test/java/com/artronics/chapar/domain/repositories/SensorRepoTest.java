package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SensorRepoTest extends BaseRepoTestConfig{
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private SensorRepo sensorRepo;

    private UnicastAddress ua;

    private Sensor sensor;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        ua = UnicastAddress.create(client,100L);
        addressRepo.save(ua);
    }

    @Test
    public void it_should_save_sensor() throws Exception {
        sensor = Sensor.create(ua);

        sensorRepo.save(sensor);

        assertThat(sensor.getId(),is(notNullValue()));
    }
}