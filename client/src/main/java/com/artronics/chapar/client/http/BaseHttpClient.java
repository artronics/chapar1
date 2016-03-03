package com.artronics.chapar.client.http;

import com.artronics.chapar.domain.entities.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.beans.factory.annotation.Value;
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
        httpClient = HttpClients.custom().setDefaultHeaders(headers).build();
    }

    public BaseHttpClient(String controllerUrl) throws URISyntaxException {
        this();
        this.controllerUrl = controllerUrl;
    }

    public Client registerClient(Client client, Long sinkAddress) throws URISyntaxException, IOException {
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(controllerUrl)
                .setPath("/client/register")
                .setParameter("sinkAddress",sinkAddress.toString())
                .build();

        String jsonClient = toJson(client);

        log.debug("Sending registration request to: "+uri);
        CloseableHttpResponse response=sendRequest(jsonClient,uri);
        System.out.println(response.getStatusLine());
        HttpEntity entity = response.getEntity();

        String msg = EntityUtils.toString(entity);
        Client registeredClient = (Client) toObject(msg, Client.class);

        log.debug("Registration successful. Associated id: "+ registeredClient.getId());

        return registeredClient;
    }

    public CloseableHttpResponse sendRequest(String msg, URI uri) throws IOException {
        CloseableHttpResponse response = null;

        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(new StringEntity(msg));
        logReq(httpPost);
        response = httpClient.execute(httpPost);

        return response;
    }

    public CloseableHttpResponse sendRequest(String msg, Long deviceId, String... segments)
            throws IOException {
        CloseableHttpResponse response = null;

        try {
            URI uri = createUri(deviceId, segments);
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(msg));
            logReq(httpPost);
            response = httpClient.execute(httpPost);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return response;
    }

    private static void logReq(HttpPost httpPost) {

    }

    public URI createUri(Long deviceId, String... segments) throws URISyntaxException {
        String path = "/client";

        if (deviceId != null) {
            path += "/" + deviceId.toString();
        }

        for (String segment : segments) {
            path += "/" + segment;
        }

        return new URIBuilder()
                .setScheme("http")
                .setHost(controllerUrl)
                .setPath(path)
                .build();
    }

    public URI createUri(String... segments) throws URISyntaxException {
        return createUri(null, segments);
    }

    public static String toJson(Object msg) throws JsonProcessingException {

        return OBJECT_MAPPER.writeValueAsString(msg);
    }

    protected static Object toObject(String msg , Class clazz) throws IOException {

        return OBJECT_MAPPER.readValue(msg,clazz);
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

    protected Object toObject(CloseableHttpResponse response,Class clazz) throws IOException {
        HttpEntity entity = response.getEntity();
        String j = EntityUtils.toString(entity);

        return OBJECT_MAPPER.readValue(j,clazz);
    }


    @Value("${com.artronics.chapar.controller.url}")
    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }

}
