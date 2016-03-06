package com.artronics.chapar.test;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@ComponentScan(basePackages = {
        "com.artronics.chapar.test.analyzer",
        "com.artronics.chapar.domain",
        "com.artronics.chapar.controller.repositories"
})
@PropertySource(value = {"classpath:application.properties",
"classpath:application-dev.properties"})
public class AnalyzerApplication {
    private final static Logger log = Logger.getLogger(AnalyzerApplication.class);
    public static void main(String[] args){
//        SpringApplication.run(AnalyzerApplication.class, args);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AnalyzerApplication.class);
        context.start();

    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
