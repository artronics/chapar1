package com.artronics.chapar.controller.controller;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.controller.services.AddressRegistrationService;
import com.artronics.chapar.controller.services.SensorRegistrationService;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.address.Address;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class AbstractNetworkControllerTest {
    @InjectMocks
    private DefaultNetworkController networkController;

    @Mock
    private AddressRegistrationService addressRegistrationService;
    @Mock
    private SensorRegistrationService sensorRegistrationService;

    private Packet packet;

    private UnicastAddress srcAdd;
    private Address dstAdd;
    private Sensor srcSensor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Controller ctrl = new Controller(1L);
        Client client = new Client(10L,ctrl);
        srcAdd = UnicastAddress.create(client,1000L);
        dstAdd= UnicastAddress.create(client,2000L);

        packet = new Packet();
        packet.setSrcLocalAddress(1000L);
        packet.setDstLocalAddress(2000L);
        packet.setSrcAddress(srcAdd);
        packet.setDstAddress(dstAdd);

        srcSensor = Sensor.create(srcAdd);
    }

    @Test
    public void it_should_register_src_node() throws Exception {
        networkController.processPacket(packet);

        verify(sensorRegistrationService,times(1)).registerSensor(srcSensor);
    }

    @Test
    public void it_should_ask_addressRegistration_for_resolving_dst_addr() throws Exception {
        networkController.processPacket(packet);

        verify(addressRegistrationService,times(1)).resolveAddress(dstAdd);
    }

    @Test
    public void it_should_ask_for_registering_sensor_coresponding_to_resolved_addresses() throws Exception {
        //since in this case dst add is a unicast address, resolved addresses contains only one
        //address which is destination unicast address.
        when(addressRegistrationService.resolveAddress(dstAdd)).thenReturn(new ArrayList<UnicastAddress>(
                Arrays.asList((UnicastAddress) dstAdd)
        ));

        networkController.processPacket(packet);

        verify(sensorRegistrationService,times(1)).registerSensor(eq(Sensor.create((UnicastAddress) dstAdd)));
    }


}