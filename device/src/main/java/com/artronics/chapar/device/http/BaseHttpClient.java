package com.artronics.chapar.device.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Lists;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class BaseHttpClient {
    private final static Logger log = Logger.getLogger(BaseHttpClient.class);

    private CloseableHttpClient httpClient;

    private String controllerUrl;

    private Long deviceId;

    private URI uri;

    public BaseHttpClient() {
        Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        List<Header> headers = Lists.newArrayList();
        headers.add(header);
        httpClient= HttpClients.custom().setDefaultHeaders(headers).build();
    }

    public BaseHttpClient(String controllerUrl, Long deviceId) throws URISyntaxException {
        this();
        this.controllerUrl = controllerUrl;
        this.deviceId = deviceId;
        uri = new URIBuilder()
                .setScheme("http")
                .setHost(controllerUrl)
                .setPath("/device/"+deviceId+"/")
                .build();
    }

    public CloseableHttpResponse sendRequest(Object msg, String path){
        throw new NotImplementedException();
    }

    public CloseableHttpResponse sendRequest(String msg, URI uri) throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:8080/device/3/buffer");
        httpPost.setEntity(new StringEntity(msg));


        try (CloseableHttpResponse response2 = httpClient.execute(httpPost)) {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);
        }
        throw new NotImplementedException();
    }

    protected static String toJson(Object msg) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(msg);
    }
}
