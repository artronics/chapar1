package com.artronics.chapar.controller.config;

import com.artronics.chapar.controller.entities.Client;
import com.artronics.chapar.domain.map.NetworkMap;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ControllerResourceConfig {
    private final static Logger log = Logger.getLogger(ControllerResourceConfig.class);

    @Bean(name = "registeredDevices")
    public Map<Client,NetworkMap> getRegisteredDevices(){
        return new HashMap<>();
    }

}
