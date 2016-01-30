package com.artronics.chapar.domain.repositories;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement

@EnableJpaRepositories(basePackages = {
        "com.artronics.chapar.controller.repositories",
        "com.artronics.chapar.domain.repositories",
        "com.artronics.chapar.domain.repositories.jpa",
        "com.artronics.chapar.client.repositories"
})
@EntityScan(basePackages = {
        "com.artronics.chapar.controller.entities",
        "com.artronics.chapar.domain.entities"
})
public class RepositoryConfig {
}
