package com.artronics.chapar.core.repositories;

import com.artronics.chapar.core.BaseCoreTestConfig;
import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseCoreTestConfig.class,PersistenceConfig.class})
@TestPropertySource("classpath:mysql-config.properties")
public class BaseRepoTest {
    private final static Logger log = Logger.getLogger(BaseRepoTest.class);
}
