package com.gr.learningpath.config;

import com.gr.learningpath.config.properties.ApplicationProperties;
import com.gr.learningpath.config.properties.CorsProperties;
import com.gr.learningpath.config.properties.DataSourceProperties;
import com.gr.learningpath.config.properties.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "learingpath.app")
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "learingpath.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean
    @ConfigurationProperties(prefix = "learingpath.security")
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "learingpath.cors")
    public CorsProperties corsProperties() {
        return new CorsProperties();
    }

}
