package com.artronics.chapar.test;

import com.artronics.chapar.device.DeviceApplication;
import com.artronics.chapar.device.http.BaseHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

//@SpringBootApplication
@Import({
        BaseHttpClient.class
})
@ComponentScan("com.artronics.chapar.test")
@PropertySource("classpath:chapar-test.properties")
public class ChaparTestApplication {

	public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ChaparTestApplication.class
        );
        context.start();
	}

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
