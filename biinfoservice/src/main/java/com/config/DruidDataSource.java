package com.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DruidDataSource {

    @Bean
    public DataSource dataSource(Environment environment) {
        return DruidDataSourceBuilder
                .create()
                .build(environment, "spring.datasource.druid.");
    }
}