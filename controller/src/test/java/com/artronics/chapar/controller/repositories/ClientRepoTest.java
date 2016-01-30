package com.artronics.chapar.controller.repositories;

import com.artronics.chapar.controller.BaseControllerTestConfig;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.ClientRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseControllerTestConfig.class,PersistenceConfig.class})
@TestPropertySource("classpath:controller-test-config.properties")
public class ClientRepoTest {

    @Autowired
    ClientRepo clientRepo;

    private Client client;

    @Test
    public void it_should_save_a_Client() throws Exception {
        client = new Client();
        clientRepo.save(client);

        Client c = clientRepo.findOne(client.getId());

        assertThat(c.getId(),is(notNullValue()));
    }
}