package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import org.hamcrest.Matchers;
import org.junit.Before;
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

public class BufferRepoTest extends BaseRepoTestConfig {

    @Autowired
    private BufferRepo bufferRepo;


    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
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
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(RX);
        b.setClient(client);

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
        Buffer b = new Buffer(Arrays.asList(1,2,3,4,5));
        b.setDirection(RX);

        bufferRepo.persist(b,client.getId());
    }

    /*
        Test getReadyTxBuffers. this query looks for particular client TX buffers which
        sentAt field is null. it means these buffers are pending to be sent by client to sensor
     */
    @Test
    public void it_should_return_tx_ready_buffers() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3), RX,client);
        Buffer b2 = new Buffer(Arrays.asList(1,2,3), RX,client);
        Buffer b3 = new Buffer(Arrays.asList(1,2,3), TX,client);
        Buffer b4 = new Buffer(Arrays.asList(1,2,3), TX,client);
        Buffer b5 = new Buffer(Arrays.asList(1,2,3), TX,client);

        bufferRepo.save(new ArrayList<Buffer>(Arrays.asList(b,b2,b3,b4,b5)));

        List<Buffer> readyBuffs= bufferRepo.getReadyTxBuffers(client);

        assertThat(readyBuffs.size(),is(equalTo(3)));
    }

    @Test
    public void it_should_just_select_passed_client_buffers() throws Exception {
        Client c2 = new Client(controller);
        clientRepo.save(c2);

        Buffer b = new Buffer(Arrays.asList(1,2,3), RX,client);
        Buffer b2 = new Buffer(Arrays.asList(1,2,3), RX,client);
        Buffer b3 = new Buffer(Arrays.asList(1,2,3), TX,c2);//This is for client 2
        Buffer b4 = new Buffer(Arrays.asList(1,2,3), TX,client);
        Buffer b5 = new Buffer(Arrays.asList(1,2,3), TX,client);

        bufferRepo.save(new ArrayList<Buffer>(Arrays.asList(b,b2,b3,b4,b5)));

        List<Buffer> readyBuffs= bufferRepo.getReadyTxBuffers(client);

        assertThat(readyBuffs.size(),is(equalTo(2)));
    }

    @Test
    public void it_should_send_those_buffers_with_sentAt_equals_null() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3), RX,client);
        Buffer b2 = new Buffer(Arrays.asList(1,2,3), RX,client);
        Buffer b3 = new Buffer(Arrays.asList(1,2,3), TX,client);
        b3.setSentAt(new Date());
        Buffer b4 = new Buffer(Arrays.asList(1,2,3), TX,client);
        Buffer b5 = new Buffer(Arrays.asList(1,2,3), TX,client);

        bufferRepo.save(new ArrayList<Buffer>(Arrays.asList(b,b2,b3,b4,b5)));

        List<Buffer> readyBuffs= bufferRepo.getReadyTxBuffers(client);

        assertThat(readyBuffs.size(),is(equalTo(2)));
    }

    @Test
    public void it_should_return_empty_list_when_there_is_no_buffer() throws Exception {
        List<Buffer> readyBuffs= bufferRepo.getReadyTxBuffers(client);

        assertThat(readyBuffs.size(),is(equalTo(0)));
    }

    @Test
    public void it_should_getProcessedRxBuffs() throws Exception {
        Buffer b = new Buffer(Arrays.asList(1,2,3), RX,client);
        b.setProcessedAt(new Date());

        Buffer b2 = new Buffer(Arrays.asList(1,2,3), RX,client);

        Buffer b3 = new Buffer(Arrays.asList(1,2,3), TX,client);
        Buffer b4 = new Buffer(Arrays.asList(1,2,3), TX,client);
        b4.setProcessedAt(new Date());

        bufferRepo.save(new ArrayList<>(Arrays.asList(b,b2,b3)));

        List<Buffer> buffs = bufferRepo.getProcessedRxBuffs();

        assertThat(buffs.size(),is(equalTo(1)));
    }

}