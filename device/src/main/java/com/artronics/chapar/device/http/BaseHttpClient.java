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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class BaseHttpClient {
    private final static Logger log = Logger.getLogger(BaseHttpClient.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private CloseableHttpClient httpClient;

    private String controllerUrl;

    public BaseHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public BaseHttpClient() {
        Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        List<Header> headers = Lists.newArrayList();
        headers.add(header);
        httpClient= HttpClients.custom().setDefaultHeaders(headers).build();
    }

    public BaseHttpClient(String controllerUrl) throws URISyntaxException {
        this();
        this.controllerUrl = controllerUrl;
    }

    public String sendRequest(String msg,Long deviceId, String... segments)
            throws IOException {
        CloseableHttpResponse response = null;
        HttpPost httpPost;
        String res = null;
        try {
            URI uri= createUri(deviceId,segments);
            httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(toJson(msg)));


            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            res= EntityUtils.toString(entity);

            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        finally {
            if (response != null) {
                response.close();
            }
        }

        return res;
    }

    public URI createUri(Long deviceId,String... segments) throws URISyntaxException {
        String path = "/device";

        if (deviceId != null) {
            path+="/"+deviceId.toString();
        }

        for (String segment : segments) {
            path+="/"+segment;
        }

        return new URIBuilder()
                .setScheme("http")
                .setHost(controllerUrl)
                .setPath(path)
                .build();
    }

    public URI createUri(String... segments) throws URISyntaxException {
        return createUri(null,segments);
    }

    protected static String toJson(Object msg) throws JsonProcessingException {

        return OBJECT_MAPPER.writeValueAsString(msg);
    }

    protected static Object toObject(String msg , Class clazz) throws IOException {

        return OBJECT_MAPPER.readValue(msg,clazz);
    }

    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }
}
