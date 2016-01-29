package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.controller.services.DeviceRegistrationService;
import com.artronics.chapar.domain.entities.Client;
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

public class ClientRegistrationControllerTest extends BaseControllerTest{

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
        Client client = new Client();
        when(registrationService.registerDevice(client)).thenReturn(client);
        String jDevice = toJson(client);

        mockMvc.perform(post("/client/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jDevice))
                .andDo(print())
                .andExpect(status().isOk());

    }
}