package com.gr.learningpath.config;

import com.gr.learningpath.mapper.MapperRegistry;
import com.gr.learningpath.mapper.MapperRegistryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public MapperRegistry mapperRegistry() {
        return new MapperRegistryImpl();
    }
}
