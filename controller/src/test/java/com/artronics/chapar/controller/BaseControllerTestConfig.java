package com.artronics.chapar.controller;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class BaseControllerTestConfig {
    private final static Logger log = Logger.getLogger(BaseControllerTestConfig.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
