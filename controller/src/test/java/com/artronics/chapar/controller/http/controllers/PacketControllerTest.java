package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Packet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PacketControllerTest  extends BaseControllerTest{
    @InjectMocks
    private PacketController packetController;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockMvc = MockMvcBuilders.standaloneSetup(packetController).build();
    }

    @Test
    public void it_should_receive_a_packet() throws Exception {
        Packet packet = Packet.create(new Buffer(Arrays.asList(1, 2, 3)));
        String jPacket = toJson(packet);

        mockMvc.perform(post("/device/1/packet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jPacket))
                .andDo(print())
                .andExpect(status().isOk());

    }
}