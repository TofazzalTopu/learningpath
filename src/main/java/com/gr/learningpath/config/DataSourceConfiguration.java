package com.gr.learningpath.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.gr.learningpath.config.properties.DataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {

    @Nonnull
    private final DataSourceProperties dataSourceProperties;

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(dataSourceProperties.getDriverClassName());
        config.setJdbcUrl(dataSourceProperties.getUrl());
        config.setUsername(dataSourceProperties.getUsername());
        config.setPassword(dataSourceProperties.getPassword());
        config.setMaximumPoolSize(dataSourceProperties.getMaximumPoolSize());
        return new HikariDataSource(config);
    }
}
