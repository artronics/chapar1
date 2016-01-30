package com.artronics.chapar.client;

import com.artronics.chapar.client.driver.DeviceDriver;
import com.artronics.chapar.client.events.BufferReadyEvent;
import com.artronics.chapar.client.http.BaseHttpClient;
import com.artronics.chapar.domain.entities.Client;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class ClientInitializer implements ApplicationListener<ContextRefreshedEvent>{
    private final static Logger log = Logger.getLogger(ClientInitializer.class);

    private String controllerUrl;

    private Long sinkAddress;

    private DeviceDriver deviceDriver;

    private BaseHttpClient httpClient;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        startDevice();

        Client client = new Client();
        try {
            httpClient.registerClient(client,sinkAddress);


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startDevice(){
        deviceDriver.init();
        deviceDriver.open();
    }

    @EventListener
    public void bufferReadyHandler(BufferReadyEvent e){
    }

    @Autowired
    @Qualifier("fakeDeviceDriver")
    public void setDeviceDriver(DeviceDriver deviceDriver) {
        this.deviceDriver = deviceDriver;
    }

    @Autowired
    public void setHttpClient(BaseHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Value("${com.artronics.chapar.client.sink_address}")
    public void setSinkAddress(Long sinkAddress) {
        this.sinkAddress = sinkAddress;
    }

    @Value("${com.artronics.chapar.controller.url}")
    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }
}
