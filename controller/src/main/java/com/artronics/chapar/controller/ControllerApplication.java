package com.artronics.chapar.controller;

import org.apache.log4j.Logger;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@ComponentScan("com.artronics.chapar.controller")
@EnableAutoConfiguration
public class ControllerApplication {
    private final static Logger log = Logger.getLogger(ControllerApplication.class);

    public static void main(String[] args){
        log.debug("kir");
        new SpringApplicationBuilder(
                ControllerApplication.class
        )
                .bannerMode(Banner.Mode.OFF)
                .run();
    }
}
