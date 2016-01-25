package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Address;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UnicastAddressRegistrationServiceTest {
    @InjectMocks
    private UnicastAddressRegistrationService addressResolver;

    private Set<Address> unicastAddressSpace;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        unicastAddressSpace = new HashSet<>();
        addressResolver.setUnicastAddressSpace(unicastAddressSpace);
    }

    @Test
    public void it_should_add_src_and_dst_address_if_they_are_not_already_there(){
        Address a = Address.create(30L);
        Address b = Address.create(33L);
        addressResolver.resolveAddress(a,b);

        assertThat(unicastAddressSpace.contains(a),is(true));
        assertThat(unicastAddressSpace.contains(b),is(true));
    }

}