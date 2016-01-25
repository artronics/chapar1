package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Link;
import com.artronics.chapar.core.entities.Node;
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

    /*
        Neighbors Address Registration
     */

    @Test
    public void it_should_add_addresses_to_registeredAddresses_if_necessary() throws Exception {
        Device device = new Device(23L);
        Set<Link> links = createLinks(5,device);

        addressResolver.registerNeighborsAddress(links);

        links.forEach(link -> {
            assertThat(
            unicastAddressSpace.contains(link.getDstNode().getAddress())
            ,is(true));
        });

    }
    private Set<Link> createLinks(int num,Device device){
        Set<Link> links = new HashSet<>();
        for (int i = 0; i < num; i++) {
            Link link = new Link(
                    Node.create(
                            Address.create(device,Integer.toUnsignedLong(i))
                    )
            );
            links.add(link);
        }

        return links;
    }
}