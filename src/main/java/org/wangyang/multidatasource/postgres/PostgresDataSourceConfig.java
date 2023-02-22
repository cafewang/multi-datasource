package org.wangyang.multidatasource.postgres;

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
        basePackages = "org.wangyang.multidatasource.postgres.repo",
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager"
)
public class PostgresDataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.postgres")
    public DataSourceProperties postgresDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource postgresDataSource(@Qualifier("postgresDataSourceProps") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa.postgres")
    public JpaProperties postgresJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa.postgres.hibernate")
    public HibernateProperties postgresHibernateProperties() {
        return new HibernateProperties();
    }

    @Bean
    public EntityManagerFactoryBuilder postgresEntityManagerFactoryBuilder(
            @Qualifier("postgresHibernateProperties") HibernateProperties hibernateProperties,
            @Qualifier("postgresJpaProperties") JpaProperties jpaProperties) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), properties, null);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
            @Qualifier("postgresDataSource") DataSource dataSource,
            @Qualifier("postgresEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages("org.wangyang.multidatasource.postgres.entity")
                .persistenceUnit("postgres")
                .build();
    }

    @Bean
    public EntityManager postgresEntityManager(@Qualifier("postgresEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager postgresTransactionManager(@Qualifier("postgresEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
