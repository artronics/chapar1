package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.BaseCoreTestConfig;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseRepoTestConfig.class,BaseCoreTestConfig.class,PersistenceConfig.class})
//@TestPropertySource("classpath:h2-config.properties")
@TestPropertySource("classpath:mysql-config.properties")
@Ignore("This is a parent config class. No tests here!")
public class BaseRepoTestConfig {
    @Autowired
    private ControllerRepo controllerRepo;
    @Autowired
    protected ClientRepo clientRepo;

    protected Controller controller;
    protected Client client;


    @Before
    public void setUp() throws Exception {
        controller = new Controller();
        controllerRepo.save(controller);

        client = new Client(controller);
        clientRepo.save(client);
    }

    @Configuration
    @ComponentScan(basePackages = {
            "com.artronics.chapar.domain"
    })
    static class Config{

    }
}
