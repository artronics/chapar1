package com.artronics.chapar.domain.repositories.jpa;

import com.artronics.chapar.domain.BaseCoreTestConfig;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.DeviceRepo;
import com.artronics.chapar.domain.repositories.PersistenceConfig;
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
public class ClientRepoTest {

    @Autowired
    protected DeviceRepo deviceRepo;

    @Test
    public void it_should_persist_a_device(){
        Client client = new Client();
        deviceRepo.save(client);

        assertNotNull(client.getId());
    }

}