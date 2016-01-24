package com.artronics.chapar.controller.config;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
public class ControllerResourceConfig {
    private final static Logger log = Logger.getLogger(ControllerResourceConfig.class);

    @Bean(name = "registeredDevices")
    public Map<Long,Device> getRegisteredDevices(){
        return new HashMap<>();
    }

    @Bean(name = "unicastAddressSpace")
    public Set<Address> getUnicastAddressSpace(){
        return new HashSet<>();
    }

}
