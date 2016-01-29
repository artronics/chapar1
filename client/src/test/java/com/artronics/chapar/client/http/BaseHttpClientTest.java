package com.artronics.chapar.client.http;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseHttpClientTest {
    private BaseHttpClient client;

    @Before
    public void setUp() throws Exception {
        client = new BaseHttpClient("foo.com");
    }

    @Test
    public void it_should_append_segments() throws URISyntaxException {
        URI act = client.createUri("register");
        String exp = "http://foo.com/client/register";

        assertThat(act.toString(), is(equalTo(exp)));
    }

    @Test
    public void it_should_append_device_id() throws URISyntaxException {
        URI act = client.createUri(3L, "buffer/register");
        String exp = "http://foo.com/client/3/buffer/register";

        assertThat(act.toString(), is(equalTo(exp)));
    }

    @Test
    public void it_should_work_without_segments() throws URISyntaxException {
        URI act = client.createUri(3L);
        String exp = "http://foo.com/client/3";

        assertThat(act.toString(), is(equalTo(exp)));
    }

}