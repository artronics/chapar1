package com.artronics.chapar.controller;

import com.artronics.chapar.controller.config.ControllerResourceConfig;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Import({ControllerResourceConfig.class})
public class BaseControllerTest {
    private final static Logger log = Logger.getLogger(BaseControllerTest.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
