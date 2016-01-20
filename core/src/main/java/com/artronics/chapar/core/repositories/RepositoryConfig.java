package com.artronics.chapar.core.repositories;

import org.apache.log4j.Logger;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.artronics.chapar.core.repositories")
@EnableTransactionManagement
@EntityScan(basePackages = "com.artronics.chapar.core.entities")
public class RepositoryConfig
{
    private final static Logger log = Logger.getLogger(RepositoryConfig.class);
}
