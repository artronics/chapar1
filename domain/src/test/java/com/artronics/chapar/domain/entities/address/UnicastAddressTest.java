package com.artronics.chapar.domain.entities.address;

import com.artronics.chapar.domain.entities.Client;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UnicastAddressTest {

    Client client;

    UnicastAddress aAdd;
    UnicastAddress sameAdd;
    UnicastAddress anotherAdd;

    @Before
    public void setUp() throws Exception {
        client = new Client(87l);

        aAdd = UnicastAddress.create(client,1L);
        sameAdd = UnicastAddress.create(client,1L);
        anotherAdd = UnicastAddress.create(client,2L);
    }

    @Test
    public void general_equality(){
        assertThat(aAdd,is(not(equalTo(null))));

        assertThat(aAdd,is(equalTo(aAdd)));

        assertThat(aAdd,is(equalTo(sameAdd)));
        assertThat(sameAdd,is(equalTo(aAdd)));
    }

    @Test
    public void two_add_with_diff_client_are_not_equal(){
        Client otherClient = new Client(2323L);
        sameAdd.setClient(otherClient);

        assertThat(aAdd,is(not(equalTo(sameAdd))));
        assertThat(sameAdd,is(not(equalTo(aAdd))));
    }

    @Test
    public void test_hashCode(){
        assertTrue(aAdd.equals(sameAdd));

        assertThat(aAdd.hashCode(),is(equalTo(sameAdd.hashCode())));
    }
}