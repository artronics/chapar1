package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.core.entities.Buffer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PacketControllerTest {

    @InjectMocks
    PacketController packetController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(packetController).build();
    }

    @Test
    public void it_should_receive_a_json_of_Buffer() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Buffer buffer = new Buffer(Arrays.asList(1, 2, 3));
        String buff = mapper.writeValueAsString(buffer);

        mockMvc.perform(post("/device/1/buffer")
                .contentType(MediaType.APPLICATION_JSON)
        .content(buff))
                .andDo(print())
                .andExpect(status().isOk());
    }
}