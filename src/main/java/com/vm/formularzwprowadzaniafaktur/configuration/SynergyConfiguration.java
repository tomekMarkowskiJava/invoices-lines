package com.vm.formularzwprowadzaniafaktur.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",
basePackages = "com.vm.formularzwprowadzaniafaktur.repository.synergyrepository")
public class SynergyConfiguration {

    @Bean(name="dataSource1")
    @ConfigurationProperties(prefix = "spring.datasource1")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
