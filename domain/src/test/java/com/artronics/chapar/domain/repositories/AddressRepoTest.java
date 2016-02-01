package com.artronics.chapar.domain.repositories;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.address.Address;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class AddressRepoTest extends BaseRepoTestConfig{
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private ControllerRepo controllerRepo;

    private Client client;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Controller ctrl = new Controller();
        controllerRepo.save(ctrl);

        client = new Client(ctrl);
        clientRepo.save(client);
    }

    @Test
    public void it_should_save_Address() throws Exception {
        Address a = new Address();
        addressRepo.save(a);

        assertThat(a.getId(),is(notNullValue()));
    }

    @Test
    public void it_should_save_unicastAddress() throws Exception {
        UnicastAddress ua = new UnicastAddress();
        ua.setClient(client);
        addressRepo.save(ua);

        assertThat(ua.getId(),is(notNullValue()));
    }
}