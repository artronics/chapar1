package com.artronics.chapar.client;

import com.artronics.chapar.domain.entities.Buffer;
import com.artronics.chapar.domain.entities.Client;
import com.artronics.chapar.domain.repositories.PersistenceConfig;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
@EnableAutoConfiguration
@EnableScheduling
@PropertySource({"classpath:client.properties","classpath:application-dev.properties"})
@ComponentScan(basePackages = {"com.artronics.chapar.client","com.artronics.chapar.domain"})
@Import(PersistenceConfig.class)
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

    @Bean(name = "txBufferQueue")
    public BlockingQueue<Buffer> getTxBufferQueue(){
        return new LinkedBlockingQueue<>();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
