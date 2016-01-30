package com.artronics.chapar.controller;

import com.artronics.chapar.domain.repositories.PersistenceConfig;
import org.apache.log4j.Logger;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = {"com.artronics.chapar.controller","com.artronics.chapar.domain"})
@Import(PersistenceConfig.class)
public class ControllerApplication {
    private final static Logger log = Logger.getLogger(ControllerApplication.class);

    public static void main(String[] args){
        new SpringApplicationBuilder(
                ControllerApplication.class
        )
                .bannerMode(Banner.Mode.OFF)
                .run();
    }
}
