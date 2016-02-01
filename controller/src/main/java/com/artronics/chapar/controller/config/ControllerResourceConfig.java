package com.artronics.chapar.controller.config;

import com.artronics.chapar.controller.entities.packet.Packet;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.entities.Sensor;
import com.artronics.chapar.domain.entities.address.UnicastAddress;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class ControllerResourceConfig {
    private final static Logger log = Logger.getLogger(ControllerResourceConfig.class);

    @Bean(name = "registeredClients")
    public Map<Client, Client> getRegisteredClients(){
        return new HashMap<>();
    }

    @Bean(name = "packetQueue")
    public BlockingQueue<Packet> getPacketQueue(){
        return new LinkedBlockingQueue<>();
    }

    @Bean(name = "unicastAddresses")
    public Map<UnicastAddress,UnicastAddress> getUnicastAddresses(){
        return new HashMap<>();
    }

    @Bean(name = "registeredSensors")
    public Map<Sensor,Sensor> getRegisteredSensors(){
        return new HashMap<>();
    }

}
