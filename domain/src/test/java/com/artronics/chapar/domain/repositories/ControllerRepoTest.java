package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.BaseCoreTestConfig;
import com.artronics.chapar.domain.entities.Controller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseCoreTestConfig.class,PersistenceConfig.class})
@TestPropertySource("classpath:h2-config.properties")
@ComponentScan("com.artronics.chapar.domain.repositories")
public class ControllerRepoTest {
    @Autowired
    private ControllerRepo controllerRepo;

    @Test
    public void it_should_save_controller() throws Exception {
        Controller ctrl = new Controller();

        controllerRepo.save(ctrl);
    }
}