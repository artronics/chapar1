package com.artronics.chapar.device.connection;

import com.artronics.chapar.core.entities.Buffer;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.device.http.BaseHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("controllerResolverHttp")
public class ControllerResolverHttp implements ControllerResolver{
    private final static Logger log = Logger.getLogger(ControllerResolverHttp.class);

    private String controllerUrl;

    private BaseHttpClient httpClient;

    public ControllerResolverHttp() {
    }

    @Override
    public Device connect(Device device,String url) throws IOException {
        return httpClient.sendRequest(device,null,"register");
    }

    @Override
    public void sendBuffer(Buffer buffer) {

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
