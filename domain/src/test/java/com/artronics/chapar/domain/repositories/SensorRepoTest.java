package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.SensorLink;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class SensorRepoTest extends BaseRepoTestConfig{
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private SensorRepo sensorRepo;

    private UnicastAddress ua;

    private Sensor sensor;
    private Sensor s1,s2;
    private UnicastAddress a1,a2;
    private SensorLink l1,l2;
    private List<SensorLink> links = new ArrayList<>();

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        ua = UnicastAddress.create(client,100L);
        addressRepo.save(ua);

        persistNeighbors();
    }

    @Test
    public void it_should_save_sensor() throws Exception {
        sensor = Sensor.create(ua);

        sensorRepo.save(sensor);

        assertThat(sensor.getId(),is(notNullValue()));
    }

    @Test
    public void it_should_save_sensor_links() throws Exception {
        sensor = Sensor.create(ua);
        sensor.setLinks(links);

        sensorRepo.save(sensor);

        assertThat(sensor.getId(),is(notNullValue()));
    }

    @Test
    public void it_should_EAGER_ly_fetch_links() throws Exception {
        sensor = Sensor.create(ua);
        sensor.setLinks(links);

        sensorRepo.save(sensor);
        Sensor s = sensorRepo.findOne(sensor.getId());

        assertThat(s.getLinks().size(),is(equalTo(2)));
    }

    private void persistNeighbors(){
        a1 = UnicastAddress.create(client, 500L);
        a2 = UnicastAddress.create(client, 501L);
        addressRepo.save(a1);
        addressRepo.save(a2);

        s1 = Sensor.create(a1);
        s2 = Sensor.create(a2);
        sensorRepo.save(s1);
        sensorRepo.save(s2);

        l1 = SensorLink.create(sensor,100D);
        l2 = SensorLink.create(sensor,100D);

        links.add(l1);
        links.add(l2);
    }
}