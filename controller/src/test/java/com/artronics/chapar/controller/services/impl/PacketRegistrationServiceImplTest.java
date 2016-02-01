package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.address.Address;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class PacketRegistrationServiceImplTest {
    @InjectMocks
    private PacketRegistrationServiceImpl packetRegistrationService;
    @Mock
    private AddressRegistrationService addressRegistrationService;

    private Packet packet;
    private Client client;

    private Long srcLocalAdd = 1000L;
    private Long dstLocalAdd = 2000L;
    private UnicastAddress srcUAdd;
    private Address dstAdd;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        packet = new Packet();
        client = new Client(1L);

        srcUAdd = UnicastAddress.create(client,srcLocalAdd);
        dstAdd = UnicastAddress.create(client,dstLocalAdd);
    }

    @Test
    public void it_should_call_for_address_reg_if_SRC_address_is_not_registered() throws Exception {
        packet.setSrcLocalAddress(srcLocalAdd);
        packet.setDstLocalAddress(dstLocalAdd);

        packetRegistrationService.registerPacket(packet,client);

        verify(addressRegistrationService,times(1)).registerUnicastAddress(srcLocalAdd,client);
    }

    @Test
    public void it_should_call_for_address_reg_if_DST_address_is_not_registered() throws Exception {
        packet.setSrcLocalAddress(srcLocalAdd);
        packet.setDstLocalAddress(dstLocalAdd);

        packetRegistrationService.registerPacket(packet,client);

        verify(addressRegistrationService,times(1)).registerUnicastAddress(dstLocalAdd,client);
    }

    @Test
    public void it_should_set_SRC_add_with_what_is_returned_from_add_reg_service() throws Exception {
        packet.setDstLocalAddress(dstLocalAdd);
        when(addressRegistrationService.isRegistered(eq(srcUAdd))).thenReturn(false);
        when(addressRegistrationService.registerUnicastAddress(srcLocalAdd,client)).thenReturn(srcUAdd);

        packet.setSrcLocalAddress(srcLocalAdd);
        packetRegistrationService.registerPacket(packet,client);

        assertThat(packet.getSrcAddress(),is(equalTo(srcUAdd)));
    }

    @Test
    public void it_should_set_DST_add_with_what_is_returned_from_add_reg_service() throws Exception {
        packet.setSrcLocalAddress(srcLocalAdd);
        when(addressRegistrationService.isRegistered(eq(dstAdd))).thenReturn(false);
        when(addressRegistrationService.registerUnicastAddress(dstLocalAdd,client))
                .thenReturn((UnicastAddress) dstAdd);

        packet.setDstLocalAddress(dstLocalAdd);
        packetRegistrationService.registerPacket(packet,client);

        assertThat(packet.getDstAddress(),is(equalTo(dstAdd)));
    }

}