package com.artronics.chapar.client.repositories;

import com.artronics.chapar.client.BaseClientTestConfig;
import com.artronics.chapar.domain.entities.Buffer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseClientTestConfig.class,PersistenceConfig.class})
@TestPropertySource("classpath:client-test-config.properties")
public class ClientBufferRepoTest {

    @Autowired
    private ClientBufferRepo bufferRepo;

    @Test
    public void it_should_Name() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(Buffer.Direction.RX);

        bufferRepo.save(b);

        Buffer bb = bufferRepo.findOne(b.getId());

        assertThat(bb.getId(),is(notNullValue()));
    }

}