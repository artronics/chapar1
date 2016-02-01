package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import com.artronics.chapar.domain.repositories.AddressRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class AddressRegistrationServiceImplTest {
    private static final boolean REGISTERED = true;
    private static final boolean NOT_REGISTERED = false;

    private UnicastAddress thisUnicastAdd;

    @InjectMocks
    private AddressRegistrationServiceImpl addressRegistrationService;
    @Mock
    private AddressRepo addressRepo;

    private Map<UnicastAddress,UnicastAddress> unicastAddresses;

    private Client client;
    private UnicastAddress ua;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        client = new Client(1l);
        ua = UnicastAddress.create(client,100L);

        unicastAddresses = new HashMap<>();

        addressRegistrationService.setUnicastAddresses(unicastAddresses);
    }

    @Test
    public void isRegistered_test() throws Exception {
        unicastAddresses.put(ua,ua);
        assertThat(addressRegistrationService.isRegistered(ua),is(true));

        Client c = new Client(10L);
        UnicastAddress notRegAdd= UnicastAddress.create(c,123L);

        assertThat(addressRegistrationService.isRegistered(notRegAdd),is(false));
    }

    @Test
    public void it_should_return_registered_address_if_it_is_already_registered() throws Exception {
        unicastAddresses.put(ua,ua);
        UnicastAddress actUa = addressRegistrationService.registerUnicastAddress(100L,client);

        assertSame(ua,actUa);
    }

    @Test
    public void it_should_add_new_ua_to_addresses_map() throws Exception {
        UnicastAddress ua = addressRegistrationService.registerUnicastAddress(1345L,new Client(13L));

        assertThat(unicastAddresses.containsKey(ua),is(true));
    }

    @Test
    public void it_should_persis_new_address() throws Exception {
        addressRegistrationService.registerUnicastAddress(100L,client);

        verify(addressRepo,times(1)).save(eq(ua));
    }

    @Test
    public void it_should_add_persisted_add_to_map() throws Exception {
        returnAddWithId();

        addressRegistrationService.registerUnicastAddress(100L,client);

        assertThat(unicastAddresses.get(ua).getId(),is(equalTo(123L)));
    }

    @Test
    public void it_should_add_persisted_ua_as_key() throws Exception {
        returnAddWithId();

        addressRegistrationService.registerUnicastAddress(100L,client);

        Set<UnicastAddress> keySet = unicastAddresses.keySet();
        boolean isMatched = keySet.stream().anyMatch(a->a.getId().equals(123L));

        assertTrue(isMatched);
    }

    @Test
    public void it_should_return_unicastAddress_if_we_ask_for_resolving_a_unicastAddress() throws Exception {
        UnicastAddress ua = new UnicastAddress();
        unicastAddresses.put(ua,ua);

        List<UnicastAddress> resolvedAdd = addressRegistrationService.resolveAddress(ua);

        assertThat(resolvedAdd.size(),is(equalTo(1)));
        assertThat(resolvedAdd.get(0),is(equalTo(ua)));
    }

    private void returnAddWithId() {
        when(addressRepo.save(eq(ua))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            UnicastAddress ua1 = (UnicastAddress) args[0];
            ua1.setId(123L);

            return ua1;
        });
    }
}