package com.example.grpctask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class Config {
    @Bean
    public BookMapper bookMapper(){
        return new BookMapperImpl();
    }
}
