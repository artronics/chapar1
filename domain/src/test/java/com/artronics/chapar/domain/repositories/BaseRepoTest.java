package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.BaseCoreTestConfig;
import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseRepoTest.class,BaseCoreTestConfig.class,PersistenceConfig.class})
@TestPropertySource("classpath:h2-config.properties")
//@TestPropertySource("classpath:mysql-config.properties")
public class BaseRepoTest {
    private final static Logger log = Logger.getLogger(BaseRepoTest.class);

    @Configuration
    @ComponentScan(basePackages = {
            "com.artronics.chapar.domain"
    })
    static class Config{

    }
}
