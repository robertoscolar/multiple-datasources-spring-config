package br.com.datasource.config;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "br.com.datasource.domain.repository.mysql",
			entityManagerFactoryRef = "mysqlEntityManagerFactory",
			transactionManagerRef = "mysqlTransactionManager")

public class DataSourceMySQLConfig {

	@Primary
	@Bean(name = "mysqlDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.mysql")
	DataSource mysqlDataSource() {
		return DataSourceBuilder
				.create().build();
	}
	
	@Primary
	@Bean(name = "mysqlEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
			@Qualifier("mysqlDataSource") DataSource dataSource,
			EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
		
		return entityManagerFactoryBuilder
				.dataSource(dataSource)
				.packages("br.com.datasource.domain.model.mysql")
				.build();
	}
	
	@Primary
	@Bean(name = "mysqlTransactionManager")
	PlatformTransactionManager mysqlTransactionManager(
			@Qualifier("mysqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory) {
		
		return new JpaTransactionManager(Objects.requireNonNull(mysqlEntityManagerFactory.getObject()));
	}
}
