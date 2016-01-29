package com.artronics.chapar.client.http;

import com.google.api.client.util.Lists;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
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

    public CloseableHttpResponse sendRequest(String msg, Long deviceId, String... segments)
            throws IOException {
        CloseableHttpResponse response = null;

        try {
            URI uri= createUri(deviceId,segments);
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

    public URI createUri(Long deviceId,String... segments) throws URISyntaxException {
        String path = "/client";

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


    @Value("${com.artronics.chapar.client.controller.url}")
    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }

}
