package com.artronics.chapar.client;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class BaseClientTestConfig {
    private final static Logger log = Logger.getLogger(BaseClientTestConfig.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
