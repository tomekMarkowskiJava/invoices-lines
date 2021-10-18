package com.vm.formularzwprowadzaniafaktur.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(
        entityManagerFactoryRef = "anotherEntityManagerFactory",
        transactionManagerRef = "anotherTransactionManager",
        basePackages = {"com.vm.formularzwprowadzaniafaktur.sources.bi.repository"})
public class BIConfiguration {

    @Autowired
    JpaVendorAdapter jpaVendorAdapter;

    @Value("${another.datasource.url}")
    private String databaseUrl;

    @Value("${another.datasource.username}")
    private String username;

    @Value("${another.datasource.password}")
    private String password;

    @Value("${another.datasource.driverClassName}")
    private String driverClassName;

    @Value("${another.datasource.hibernate.dialect}")
    private String dialect;

    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(databaseUrl, username, password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean(name = "anotherEntityManager")
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean(name = "anotherEntityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("com.vm.formularzwprowadzaniafaktur.sources.bi.model");   // <- package for entities
        emf.setPersistenceUnitName("anotherPersistenceUnit");
        emf.setJpaProperties(properties);
        emf.afterPropertiesSet();
        return emf.getObject();
    }

    @Bean(name = "anotherTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

}
