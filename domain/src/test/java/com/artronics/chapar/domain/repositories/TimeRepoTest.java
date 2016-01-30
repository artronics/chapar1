package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.BaseCoreTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseCoreTestConfig.class,PersistenceConfig.class})
@TestPropertySource("classpath:mysql-config.properties")
@ComponentScan(basePackages = {
        "com.artronics.chapar.domain"
})
public class TimeRepoTest {

    @Autowired
    private TimeRepo timeRepo;

    @Test
    public void it_should_getServer_time() throws Exception {
        Date d = timeRepo.getDbNowTime();

    }
}