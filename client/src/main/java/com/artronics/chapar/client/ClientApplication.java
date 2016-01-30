package com.artronics.chapar.client;

import com.artronics.chapar.domain.entities.Client;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

@EnableAutoConfiguration
@Configuration
@PropertySource("classpath:client.properties")
@ComponentScan(basePackages = {"com.artronics.chapar.client"})
public class ClientApplication {
    private final static Logger log = Logger.getLogger(ClientApplication.class);

    public static void main(String[] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ClientApplication.class
        );
        context.start();
    }

    @Bean(name = "registeredClient")
    public Client getRegisteredClient(){
        return new Client();
    }

}
