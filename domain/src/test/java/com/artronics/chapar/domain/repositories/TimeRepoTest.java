package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.BaseCoreTestConfig;
import com.artronics.chapar.domain.entities.Buffer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseCoreTestConfig.class,PersistenceConfig.class, TimeRepoTest.Config.class})
@TestPropertySource("classpath:mysql-config.properties")
public class TimeRepoTest {
    @Autowired
    private TimeRepo timeRepo;

    @Autowired
    private BufferRepo bufferRepo;

//    @Ignore("just to see the time format.")
    @Test
    public void it_should_getServer_time() throws Exception {
        Date d = timeRepo.getDbNowTime();

        System.out.println(d);
    }

    @Test
    public void it_should_get_receivedAt_with_microsecond_precision() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(Buffer.Direction.RX);
        b.setReceivedAt(timeRepo.getDbNowTime());

        bufferRepo.save(b);

        Buffer perB = bufferRepo.findOne(b.getId());

        String s = perB.getReceivedAt().toString();
        s = s.substring(s.lastIndexOf(".")+1);
        assertThat(s.length(),is(equalTo(6)));
    }

    @Configuration
    @ComponentScan(basePackages = {
            "com.artronics.chapar.domain.repositories"
    })
    static class Config{

    }
}