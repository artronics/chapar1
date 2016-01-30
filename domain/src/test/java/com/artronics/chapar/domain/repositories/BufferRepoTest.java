package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.artronics.chapar.domain.entities.Buffer.Direction.RX;
import static com.artronics.chapar.domain.entities.Buffer.Direction.TX;
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

    }

    @Test
    public void it_should_save() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(RX);

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
        b.setDirection(RX);
        b.setClient(perC);

        bufferRepo.save(b);

        Buffer perB = bufferRepo.findOne(b.getId());

        assertThat(perB.getId(),is(Matchers.notNullValue()));
        assertThat(perB.getDirection(),is(equalTo(RX)));
        assertThat(perB.getClient(),is(Matchers.notNullValue()));
    }

    @Test
    public void it_should_EAGER_ly_load_buffer_content() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(RX);

        bufferRepo.save(b);

        Buffer perB = bufferRepo.findOne(b.getId());

        assertThat(perB.getContent().size(),is(equalTo(5)));
    }

    @Test
    public void mysql_should_create_a_timestamp_for_created_field() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(RX);

        bufferRepo.save(b);

        Buffer perB = bufferRepo.findOne(b.getId());

        assertThat(perB.getCreated(),is(Matchers.notNullValue()));
    }

    @Test
    public void it_should_persist() throws Exception {
        Client c = new Client();
        clientRepo.save(c);

        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(RX);

        bufferRepo.persist(b,c.getId());
    }

    /*
        Test getReadyTxBuffers. this query looks for particular client TX buffers which
        sentAt field is null. it means these buffers are pending to be sent by client to sensor
     */
    @Test
    public void it_should_return_tx_ready_buffers() throws Exception {
        Client c = new Client();
        clientRepo.save(c);

        Buffer b = new Buffer(Arrays.asList(1,2,3), RX,c);
        Buffer b2 = new Buffer(Arrays.asList(1,2,3), RX,c);
        Buffer b3 = new Buffer(Arrays.asList(1,2,3), TX,c);
        Buffer b4 = new Buffer(Arrays.asList(1,2,3), TX,c);
        Buffer b5 = new Buffer(Arrays.asList(1,2,3), TX,c);

        bufferRepo.save(new ArrayList<Buffer>(Arrays.asList(b,b2,b3,b4,b5)));

        List<Buffer> readyBuffs= bufferRepo.getReadyTxBuffers(c);

        assertThat(readyBuffs.size(),is(equalTo(3)));
    }

    @Test
    public void it_should_just_select_passed_client_buffers() throws Exception {
        Client c = new Client();
        clientRepo.save(c);

        Client c2 = new Client();
        clientRepo.save(c2);

        Buffer b = new Buffer(Arrays.asList(1,2,3), RX,c);
        Buffer b2 = new Buffer(Arrays.asList(1,2,3), RX,c);
        Buffer b3 = new Buffer(Arrays.asList(1,2,3), TX,c2);//This is for client 2
        Buffer b4 = new Buffer(Arrays.asList(1,2,3), TX,c);
        Buffer b5 = new Buffer(Arrays.asList(1,2,3), TX,c);

        bufferRepo.save(new ArrayList<Buffer>(Arrays.asList(b,b2,b3,b4,b5)));

        List<Buffer> readyBuffs= bufferRepo.getReadyTxBuffers(c);

        assertThat(readyBuffs.size(),is(equalTo(2)));
    }

    @Test
    public void it_should_send_those_buffers_with_sentAt_equals_null() throws Exception {
        Client c = new Client();
        clientRepo.save(c);

        Buffer b = new Buffer(Arrays.asList(1,2,3), RX,c);
        Buffer b2 = new Buffer(Arrays.asList(1,2,3), RX,c);
        Buffer b3 = new Buffer(Arrays.asList(1,2,3), TX,c);
        b3.setSentAt(new Date());
        Buffer b4 = new Buffer(Arrays.asList(1,2,3), TX,c);
        Buffer b5 = new Buffer(Arrays.asList(1,2,3), TX,c);

        bufferRepo.save(new ArrayList<Buffer>(Arrays.asList(b,b2,b3,b4,b5)));

        List<Buffer> readyBuffs= bufferRepo.getReadyTxBuffers(c);

        assertThat(readyBuffs.size(),is(equalTo(2)));
    }

    @Test
    public void it_should_return__when_there_is_no_buffer() throws Exception {
        Client c = new Client();
        clientRepo.save(c);

        List<Buffer> readyBuffs= bufferRepo.getReadyTxBuffers(c);

        assertThat(readyBuffs.size(),is(equalTo(0)));
    }
}