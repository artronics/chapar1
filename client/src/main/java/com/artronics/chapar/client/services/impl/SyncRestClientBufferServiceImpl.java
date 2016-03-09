package com.artronics.chapar.client.services.impl;

import com.artronics.chapar.client.http.BaseHttpClient;
import com.artronics.chapar.domain.entities.Buffer;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component("syncRestClientBufferServiceImpl")
public class SyncRestClientBufferServiceImpl extends BaseClientBufferService {
    private final static Logger log = Logger.getLogger(SyncRestClientBufferServiceImpl.class);

    private BaseHttpClient httpClient;

    @PostConstruct
    public void initBean() {
        log.debug("Implementation of BufferService: " + SyncRestClientBufferServiceImpl.class.getSimpleName());
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public Buffer sendBufferToController(Buffer buffer,Buffer.Direction dir) {
        buffer.setDirection(dir);
        //since this is a test node we assume that this is when
        //client receives a tx packet
        //here tx means that we want to send this packet to controller then
        //controller forwards this packet and when we get it sentAt will be
        //added to RX packet
        buffer.setReceivedAt(timeRepo.getDbNowTime());

        String b = "{}";
        URI uri = null;
        CloseableHttpResponse res;
        Buffer resBuffer = null;
        try {
            uri = httpClient.createUri(registeredClient.getId(), "buffer");
            b = BaseHttpClient.toJson(buffer);

            res = httpClient.sendRequest(b, uri);

            HttpEntity entity = res.getEntity();
            String resBufferJson = EntityUtils.toString(entity);

            //if its empty it means controller dose not have any response for this packet(null)
            if (!resBufferJson.equals(""))
                resBuffer = (Buffer) BaseHttpClient.toObject(resBufferJson, Buffer.class);

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        if (resBuffer != null) {
            sendBufferToSink(resBuffer);

            resBuffer.setSentAt(timeRepo.getDbNowTime());
            persistBuffer(resBuffer, Buffer.Direction.TX);
        }

        return buffer;
    }

    @Override
    public Buffer sendBufferToController(Buffer buffer) {
        throw new NotImplementedException();
    }

    @Autowired
    public void setHttpClient(BaseHttpClient httpClient) {
        this.httpClient = httpClient;
    }

}
