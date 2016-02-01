package com.artronics.chapar.controller.services.impl;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.repositories.PacketRepo;
import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.address.Address;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class PacketRegistrationServiceImplTest {
    @InjectMocks
    private PacketRegistrationServiceImpl packetRegistrationService;
    @Mock
    private AddressRegistrationService addressRegistrationService;
    @Mock
    private PacketRepo packetRepo;

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

        srcUAdd = UnicastAddress.create(client, srcLocalAdd);
        dstAdd = UnicastAddress.create(client, dstLocalAdd);

        packet.setSrcLocalAddress(srcLocalAdd);
        packet.setDstLocalAddress(dstLocalAdd);
    }

    @Test
    public void it_should_persist_packet() throws Exception {
        packetRegistrationService.registerPacket(packet, client);

        verify(packetRepo, times(1)).save(packet);
    }

    @Test
    public void it_should_persist_packet_after_resolving_src_and_dst_addresses() throws Exception {
        stubSetSrcAndDstAdd();

        ArgumentCaptor<Packet> pc = ArgumentCaptor.forClass(Packet.class);

        packetRegistrationService.registerPacket(packet, client);
        verify(packetRepo,times(1)).save(pc.capture());

        Packet p = pc.getValue();

        assertThat(p.getSrcAddress(),is(notNullValue()));
        assertThat(p.getDstAddress(),is(notNullValue()));
    }

    private void stubSetSrcAndDstAdd() {
        when(addressRegistrationService.registerUnicastAddress(srcLocalAdd, client)).thenReturn(srcUAdd);

        when(addressRegistrationService.registerUnicastAddress(dstLocalAdd, client))
                .thenReturn((UnicastAddress) dstAdd);
    }

    /*
            Source and Destination Addresses registration
        */
    @Test
    public void it_should_call_for_address_reg_if_SRC_address_is_not_registered() throws Exception {
        packetRegistrationService.registerPacket(packet, client);

        verify(addressRegistrationService, times(1)).registerUnicastAddress(srcLocalAdd, client);
    }

    @Test
    public void it_should_call_for_address_reg_if_DST_address_is_not_registered() throws Exception {
        packetRegistrationService.registerPacket(packet, client);

        verify(addressRegistrationService, times(1)).registerUnicastAddress(dstLocalAdd, client);
    }

    @Test
    public void it_should_set_SRC_add_with_what_is_returned_from_add_reg_service() throws Exception {
        when(addressRegistrationService.registerUnicastAddress(srcLocalAdd, client)).thenReturn(srcUAdd);

        packetRegistrationService.registerPacket(packet, client);

        assertThat(packet.getSrcAddress(), is(equalTo(srcUAdd)));
    }

    @Test
    public void it_should_set_DST_add_with_what_is_returned_from_add_reg_service() throws Exception {
        when(addressRegistrationService.registerUnicastAddress(dstLocalAdd, client))
                .thenReturn((UnicastAddress) dstAdd);

        packetRegistrationService.registerPacket(packet, client);

        assertThat(packet.getDstAddress(), is(equalTo(dstAdd)));
    }

}