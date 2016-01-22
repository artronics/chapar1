package com.artronics.chapar.controller.http.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;


public class BaseControllerTest {

    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectMapper = new ObjectMapper();
    }

    protected String toJson(Object entity){
        try {
            return objectMapper.writeValueAsString(entity);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
