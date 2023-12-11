package com.liviacosta.projeto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liviacosta.projeto.mapper.MapperHandler;

@Configuration
public class ProjetoConfig {

    @Bean
    MapperHandler mapperHandler() {
    return new MapperHandler();
    }

}
