package com.artronics.chapar.test;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Import({
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
