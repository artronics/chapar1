package com.artronics.chapar.test;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.artronics.chapar.test.analyzer",
        "com.artronics.chapar.domain",
        "com.artronics.chapar.controller.repositories"
})
public class AnalyzerApplication {
    private final static Logger log = Logger.getLogger(AnalyzerApplication.class);
    public static void main(String[] args){
        SpringApplication.run(AnalyzerApplication.class, args);

    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
