package com.project.ITAM.Config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager",
        basePackages = {"com.project.ITAM"}
)
public class PrimaryDBConfig {

    @Primary
    @Bean(name="primaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name="primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                              @Qualifier("primaryDataSource") DataSource primaryDataSource) {
        return builder.dataSource(primaryDataSource)
                .packages("com.project.ITAM")
                .persistenceUnit("raw")
                .build();
    }

    @Primary
    @Bean(name="primaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory){
        return new JpaTransactionManager((EntityManagerFactory) primaryEntityManagerFactory);
    }


}
