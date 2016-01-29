package com.artronics.chapar.device;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@Configuration
@PropertySource("classpath:device.properties")
@ComponentScan(basePackages = {"com.artronics.chapar.device"})
public class DeviceApplication {
    private final static Logger log = Logger.getLogger(DeviceApplication.class);

    public static void main(String[] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DeviceApplication.class
        );
        context.start();
    }
}
