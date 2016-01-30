package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.BaseCoreTestConfig;
import com.artronics.chapar.domain.entities.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseCoreTestConfig.class,PersistenceConfig.class})
@TestPropertySource("classpath:h2-config.properties")
@ComponentScan("com.artronics.chapar.domain.repositories")
public class ClientRepoTest {

    @Autowired
    ClientRepo clientRepo;

    private Client client;

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void it_should_save_a_Client() throws Exception {
        client = new Client();
        clientRepo.save(client);

        Client c = clientRepo.findOne(client.getId());

        assertThat(c.getId(),is(notNullValue()));
    }
}