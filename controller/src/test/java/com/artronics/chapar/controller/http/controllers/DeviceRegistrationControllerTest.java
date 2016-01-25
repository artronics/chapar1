package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.controller.services.DeviceRegistrationService;
import com.artronics.chapar.core.entities.Device;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeviceRegistrationControllerTest extends BaseControllerTest{

    @InjectMocks
    private DeviceRegistrationController registrationController;

    @Mock
    private DeviceRegistrationService registrationService;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void perform_post_method_to_register_device() throws Exception {
        Device device = new Device();
        when(registrationService.registerDevice(device)).thenReturn(device);
        String jDevice = toJson(device);

        mockMvc.perform(post("/device/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jDevice))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void perform_post_method_to_register_device_and_sink() throws Exception {
        Device device = new Device();
        when(registrationService.registerDevice(device)).thenReturn(device);
        String jDevice = toJson(device);

        mockMvc.perform(post("/device/register?sinkAddress=12")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jDevice))
                .andDo(print())
                .andExpect(status().isOk());

    }


}