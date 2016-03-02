package com.artronics.chapar.controller.http.controllers;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.domain.entities.Buffer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PacketControllerTest extends BaseControllerTest{

    @InjectMocks
    PacketController packetController;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockMvc = MockMvcBuilders.standaloneSetup(packetController).build();
    }

    @Test
    public void it_should_receive_a_json_of_Buffer() throws Exception {
        Buffer buffer = new Buffer(Arrays.asList(1, 2, 3));
        String buff = toJson(buffer);

        mockMvc.perform(post("/client/1/buffer")
                .contentType(MediaType.APPLICATION_JSON)
        .content(buff))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void it_should_receive_a_json_of_Packet() throws Exception {
        Packet packet = new Packet(new Buffer(Arrays.asList(1,2,3,4)));
        String jPacket = toJson(packet);

        mockMvc.perform(post("/client/1/packet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jPacket))
                .andDo(print())
                .andExpect(status().isOk());
    }
}