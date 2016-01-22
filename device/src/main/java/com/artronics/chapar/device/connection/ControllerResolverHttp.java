package com.artronics.chapar.device.connection;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.device.http.BaseHttpClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("controllerResolverHttp")
public class ControllerResolverHttp implements ControllerResolver{
    private final static Logger log = Logger.getLogger(ControllerResolverHttp.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String controllerUrl;

    private Long deviceId;

    private BaseHttpClient httpClient;

    public ControllerResolverHttp() {
    }

    @Override
    public Device connect(Device device,String url) throws IOException {
        CloseableHttpResponse response= httpClient.sendRequest(toJson(device),null,"register");
        HttpEntity entity = response.getEntity();
        return (Device) toObject(EntityUtils.toString(entity),Device.class);
    }

    @Override
    public void sendBuffer(Buffer buffer) {

    }

    protected static String toJson(Object msg) throws JsonProcessingException {

        return OBJECT_MAPPER.writeValueAsString(msg);
    }

    protected static Object toObject(String msg , Class clazz) throws IOException {

        return OBJECT_MAPPER.readValue(msg,clazz);
    }

    @Override
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    @Autowired
    public void setHttpClient(BaseHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Value("${com.artronics.chapar.device.controller.url}")
    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }
}
