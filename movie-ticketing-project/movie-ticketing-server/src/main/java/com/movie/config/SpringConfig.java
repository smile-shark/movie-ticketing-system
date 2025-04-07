package com.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(value = "com.movie",excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
})
public class SpringConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
