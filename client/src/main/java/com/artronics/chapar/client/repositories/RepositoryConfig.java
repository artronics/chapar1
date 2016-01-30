package com.artronics.chapar.client.repositories;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {"com.artronics.chapar.client.repositories"})
@EnableTransactionManagement
@EntityScan(basePackages = {
"com.artronics.chapar.domain.entities"})
public class RepositoryConfig
{
}
