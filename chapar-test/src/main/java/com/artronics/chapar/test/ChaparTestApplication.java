package com.artronics.chapar.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.artronics.chapar.test.test",
		"com.artronics.chapar.domain"
})
public class ChaparTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChaparTestApplication.class, args);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}
}
