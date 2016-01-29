package com.artronics.chapar.core.repositories.jpa;

import com.artronics.chapar.core.BaseCoreTestConfig;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.repositories.DeviceRepo;
import com.artronics.chapar.core.repositories.PersistenceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseCoreTestConfig.class,PersistenceConfig.class})
@TestPropertySource("classpath:mysql-config.properties")
public class DeviceRepoTest {

    @Autowired
    protected DeviceRepo deviceRepo;

    @Test
    public void it_should_persist_a_device(){
        Device device = new Device();
        deviceRepo.save(device);

        assertNotNull(device.getId());
    }

}