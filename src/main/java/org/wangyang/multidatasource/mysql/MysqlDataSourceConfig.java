package org.wangyang.multidatasource.mysql;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "org.wangyang.multidatasource.mysql.repo",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager"
)
public class MysqlDataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSourceProperties mysqlDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource mysqlDataSource(@Qualifier("mysqlDataSourceProps") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa.mysql")
    public JpaProperties mysqlJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa.mysql.hibernate")
    public HibernateProperties mysqlHibernateProperties() {
        return new HibernateProperties();
    }

    @Bean
    public EntityManagerFactoryBuilder mysqlEntityManagerFactoryBuilder(
            @Qualifier("mysqlHibernateProperties") HibernateProperties hibernateProperties,
            @Qualifier("mysqlJpaProperties") JpaProperties jpaProperties) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), properties, null);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            @Qualifier("mysqlDataSource") DataSource dataSource,
            @Qualifier("mysqlEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages("org.wangyang.multidatasource.mysql.entity")
                .persistenceUnit("mysql")
                .build();
    }

    @Bean
    public EntityManager mysqlEntityManager(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager mysqlTransactionManager(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
