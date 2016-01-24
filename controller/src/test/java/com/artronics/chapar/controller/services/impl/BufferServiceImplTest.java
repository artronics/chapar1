package com.artronics.chapar.controller.services.impl;

import org.junit.Before;
import org.mockito.InjectMocks;

public class BufferServiceImplTest extends BaseServiceTest{

    @InjectMocks
    private BufferServiceImpl bufferService;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        bufferService.setRegisteredDevices(registeredDevices);
    }

}