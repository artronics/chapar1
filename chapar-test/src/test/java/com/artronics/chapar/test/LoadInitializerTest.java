package com.artronics.chapar.test;

import com.artronics.chapar.domain.entities.Buffer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadInitializerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void it_should_Name() throws Exception {
//        DataPacket value = DataPacket.create(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
//        String j =OBJECT_MAPPER.writeValueAsString(value);
//        System.out.println(j);

    }

    @Test
    public void it_should_a() throws Exception {
        Buffer b1 = new Buffer();
        Buffer b2 = new Buffer();
        List<Buffer> b = new ArrayList<>(Arrays.asList(b1,b2));
        String j =OBJECT_MAPPER.writeValueAsString(b);
        System.out.println(j);

        List<Buffer> ob = fromJSON(new TypeReference<List<Buffer>>() {
        },j);

        System.out.println(ob);
    }

    public static <T> T fromJSON(final TypeReference<T> type,
                                 final String jsonPacket) {
        T data = null;

        try {
            data = new ObjectMapper().readValue(jsonPacket, type);
        } catch (Exception e) {
            // Handle the problem
        }
        return data;
    }

}