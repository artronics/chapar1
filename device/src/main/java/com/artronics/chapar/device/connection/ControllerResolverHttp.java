package com.artronics.chapar.device.connection;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Session;
import com.artronics.chapar.device.http.BaseHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component("controllerResolverHttp")
public class ControllerResolverHttp implements ControllerResolver{
    private final static Logger log = Logger.getLogger(ControllerResolverHttp.class);

    private String controllerUrl;

    private BaseHttpClient httpClient = new BaseHttpClient();

    public ControllerResolverHttp() {
    }

    @Override
    public Session connect(String url) {
        return null;
    }

    @Override
    public void sendBuffer(Buffer buffer) {
        try {
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(controllerUrl)
                    .setPath("/device/"+"1"+"/")
                    .build();

            ObjectMapper om = new ObjectMapper();
            String buff = om.writeValueAsString(buffer);
            httpClient.sendRequest(buff,uri);

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }


    }

    @Value("${com.artronics.chapar.device.controller.url}")
    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }
}
