package br.com.datasource.config;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "br.com.datasource.domain.repository.postgres",
			entityManagerFactoryRef = "postgresEntityManagerFactory",
			transactionManagerRef = "postgresTransactionManager")

public class DataSourcePostgreSQLConfig {
	
	@Bean(name = "postgresDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.postgres")
	DataSource postgresDataSource() {
		return DataSourceBuilder
				.create()
				.build();
	}
	
	@Bean(name = "postgresEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
			@Qualifier("postgresDataSource") DataSource dataSource,
			EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
		
		return entityManagerFactoryBuilder
				.dataSource(dataSource)
				.packages("br.com.datasource.domain.model.postgres")
				.build();
	}
	
	@Bean(name = "postgresTransactionManager")
	PlatformTransactionManager postgresTransactionManager(
			@Qualifier("postgresEntityManagerFactory") LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory) {
		
		return new JpaTransactionManager(Objects.requireNonNull(postgresEntityManagerFactory.getObject()));
	}
	
}
