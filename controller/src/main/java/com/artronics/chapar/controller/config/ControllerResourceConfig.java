package com.artronics.chapar.controller.config;

import com.artronics.chapar.core.entities.Address;
import com.artronics.chapar.core.entities.Device;
import com.artronics.chapar.core.entities.Node;
import com.artronics.chapar.core.map.NodeMap;
import com.artronics.chapar.core.map.NodeMapImpl;
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

    @Bean(name = "nodeMap")
    public NodeMap getNodeMap(){
        return new NodeMapImpl();
    }

    @Bean(name = "registeredDevices")
    public Map<Long,Device> getRegisteredDevices(){
        return new HashMap<>();
    }

    @Bean(name = "registeredNodes")
    public Map<Node,Node> getRegisteredNodes(){
        return new HashMap<>();
    }

    @Bean(name = "unicastAddressSpace")
    public Set<Address> getUnicastAddressSpace(){
        return new HashSet<>();
    }

}
