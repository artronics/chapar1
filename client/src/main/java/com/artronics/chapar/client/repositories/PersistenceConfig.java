package com.artronics.chapar.client.repositories;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(RepositoryConfig.class)
public class PersistenceConfig
{
    private final static Logger log = Logger.getLogger(PersistenceConfig.class);

    @Value("${com.artronics.chapar.client.persistence.url}")
    private String url;
    @Value("${com.artronics.chapar.client.persistence.driver}")
    private String driver;
    @Value("${com.artronics.chapar.client.persistence.username}")
    private String username;
    @Value("${com.artronics.chapar.client.persistence.password}")
    private String password;
    @Value("${com.artronics.chapar.client.persistence.dialect}")
    private String dialect;
    @Value("${com.artronics.chapar.client.persistence.hbm2ddl}")
    private String hbm2ddl;

    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
    protected Properties buildHibernateProperties()
    {
        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("spring.datasource.url",
                                        url);

        hibernateProperties.setProperty("spring.datasource.driverClassName", driver);

        hibernateProperties.setProperty("spring.datasource.username", username);
        hibernateProperties.setProperty("spring.datasource.password", password);

        hibernateProperties.setProperty("hibernate.dialect", dialect);
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        hibernateProperties.setProperty("hibernate.use_sql_comments", "false");
        hibernateProperties.setProperty("hibernate.format_sql", "false");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        hibernateProperties.setProperty("hibernate.event.merge.entity_copy_observer", "allow");

        hibernateProperties.setProperty("hibernate.generate_statistics", "false");

        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf)
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource());

        emf.setPackagesToScan("com.artronics.chapar.domain.entities");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(buildHibernateProperties());

        return emf;
    }
}
