package com.artronics.chapar.controller.sdwn;

import com.artronics.chapar.controller.controller.NetworkController;
import com.artronics.chapar.controller.sdwn.packet.SdwnPacketType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class SdwnNetworkControllerTest {

    @InjectMocks
    private NetworkController<SdwnPacketType> controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void it_should_Name() throws Exception {

    }
}