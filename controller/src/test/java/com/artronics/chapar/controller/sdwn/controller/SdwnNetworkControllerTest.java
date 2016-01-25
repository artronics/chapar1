package com.artronics.chapar.controller.sdwn.controller;

import com.artronics.chapar.controller.sdwn.packet.ReportPacket;
import com.artronics.chapar.core.exceptions.MalformedPacketException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

public class SdwnNetworkControllerTest {
    
    @InjectMocks
    private SdwnNetworkController networkController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test(expected = MalformedPacketException.class)
    public void it_should_throw_exp_if_packet_is_malformed() throws Exception {
        ReportPacket rp = ReportPacket.create(Arrays.asList(1,2,3,4,5,6,7,89,9));
        networkController.processPacket(rp);

    }
}