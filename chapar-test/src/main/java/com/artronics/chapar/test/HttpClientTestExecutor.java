package com.artronics.chapar.test;

import com.artronics.chapar.client.http.BaseHttpClient;
import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Controller;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component("httpClientTestExecutor")
public class HttpClientTestExecutor extends BaseClientTestExecutor {
    private final static Logger log = Logger.getLogger(HttpClientTestExecutor.class);
    private long controllerId;
    private long clientId;
    private BaseHttpClient httpClient;



    @Override
    public void start() {
        regClient = new Client(clientId,new Controller(controllerId));

    }

    @Override
    protected void sendDataPacket() throws IOException, URISyntaxException {
        Buffer b = createDataBuff(regClient,10,30);
        String bJson= BaseHttpClient.toJson(b);
        URI uri=httpClient.createUri(clientId,"buffer");
        httpClient.sendRequest(bJson,uri);
    }

    @Value("${com.artronics.chapar.test.controller_id}")
    public void setControllerId(long controllerId) {
        this.controllerId = controllerId;
    }

    @Value("${com.artronics.chapar.test.client_id}")
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

}
