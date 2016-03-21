package com.epam.springadvanced.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Import({AspectConfiguration.class, DataConfiguration.class, AuditoriumConfiguration.class})
@PropertySource("classpath:auditorium1.properties")
@PropertySource("classpath:auditorium2.properties")
@PropertySource("classpath:auditorium3.properties")
@ComponentScan("com.epam.springadvanced.service")
public class SpringConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}
