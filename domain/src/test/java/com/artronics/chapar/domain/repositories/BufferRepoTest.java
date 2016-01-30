package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class BufferRepoTest extends BaseRepoTest{

    @Autowired
    private BufferRepo bufferRepo;

    @Autowired
    private ClientRepo clientRepo;

    @Test
    public void it_should_Name() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(Buffer.Direction.RX);

        bufferRepo.save(b);

        Buffer bb = bufferRepo.findOne(b.getId());

        assertThat(bb.getId(),is(notNullValue()));
    }

    @Test
    public void it_should_persist_buffer_with_registeredClient() throws Exception {
        Client c = new Client();
        clientRepo.save(c);
        Client perC = clientRepo.findOne(c.getId());

        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(Buffer.Direction.RX);
        b.setClient(perC);

        bufferRepo.save(b);

        Buffer perB = bufferRepo.findOne(b.getId());

        assertThat(perB.getId(),is(Matchers.notNullValue()));
        assertThat(perB.getDirection(),is(equalTo(Buffer.Direction.RX)));
        assertThat(perB.getClient(),is(Matchers.notNullValue()));
    }

    @Test
    public void it_should_EAGER_ly_load_buffer_content() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(Buffer.Direction.RX);

        bufferRepo.save(b);

        Buffer perB = bufferRepo.findOne(b.getId());

        assertThat(perB.getContent().size(),is(equalTo(5)));
    }

    @Test
    public void mysql_should_create_a_timestamp_for_created_field() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(Buffer.Direction.RX);

        bufferRepo.save(b);

        Buffer perB = bufferRepo.findOne(b.getId());

        assertThat(perB.getCreated(),is(Matchers.notNullValue()));
    }

    @Test
    public void it_should_persist() throws Exception {
        Client c = new Client();
        clientRepo.save(c);

        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(Buffer.Direction.RX);

        bufferRepo.persist(b,c.getId());
    }

}