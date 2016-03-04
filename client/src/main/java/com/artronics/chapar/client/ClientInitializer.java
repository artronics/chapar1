package com.artronics.chapar.client;

import com.artronics.chapar.client.driver.DeviceDriver;
import com.artronics.chapar.client.exceptions.ClientRegistrationFailed;
import com.artronics.chapar.client.http.BaseHttpClient;
import com.artronics.chapar.client.services.ClientBufferService;
import com.artronics.chapar.client.test.DBClientTestExecutor;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.ClientRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class ClientInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final static Logger log = Logger.getLogger(ClientInitializer.class);

    private String controllerUrl;

    private Long sinkAddress;

    private Client registeredClient;

    private DeviceDriver deviceDriver;

    private BaseHttpClient httpClient;

    private ClientBufferService bufferService;

    private ClientRepo clientRepo;

    private boolean disDeviceDriver = false;
    private boolean testRunner = false;
    private DBClientTestExecutor clientTestExecutor;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!disDeviceDriver)
            startDevice();

        Client client = new Client();
        try {
            registeredClient = httpClient.registerClient(client, sinkAddress);

            if (registeredClient.getId() == null) {
                throw new ClientRegistrationFailed();
            }

            registeredClient = clientRepo.findOne(registeredClient.getId());

            bufferService.setRegisteredClient(registeredClient);
            bufferService.start();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClientRegistrationFailed clientRegistrationFailed) {
            //TODO use an strategy to retry registration process
            clientRegistrationFailed.printStackTrace();
        }

        if (testRunner)
            startTestRunner();
    }

    private void startDevice() {
        deviceDriver.init();
        deviceDriver.open();
    }

    private void startTestRunner() {
        clientTestExecutor.setRegClient(registeredClient);
        clientTestExecutor.setDelayBeforeStart(20000);

        clientTestExecutor.start();
    }

    @Autowired
    @Qualifier("deviceDriverSerialPort")
    public void setDeviceDriver(DeviceDriver deviceDriver) {
        this.deviceDriver = deviceDriver;
    }

    @Autowired
    public void setHttpClient(BaseHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Autowired
    public void setBufferService(ClientBufferService bufferService) {
        this.bufferService = bufferService;
    }

    @Resource(name = "registeredClient")
    public void setRegisteredClient(Client registeredClient) {
        this.registeredClient = registeredClient;
    }

    @Autowired
    public void setClientRepo(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Value("${com.artronics.chapar.client.sink_address}")
    public void setSinkAddress(Long sinkAddress) {
        this.sinkAddress = sinkAddress;
    }

    @Value("${com.artronics.chapar.controller.url}")
    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }

    @Value("${com.artronics.chapar.client.test_runner}")
    public void setTestRunner(boolean testRunner) {
        this.testRunner = testRunner;
    }

    @Value("${com.artronics.chapar.client.disable_device_driver}")
    public void setDisDeviceDriver(boolean disDeviceDriver) {
        this.disDeviceDriver = disDeviceDriver;
    }

    @Autowired
    public void setClientTestExecutor(DBClientTestExecutor clientTestExecutor) {
        this.clientTestExecutor = clientTestExecutor;
    }

}
