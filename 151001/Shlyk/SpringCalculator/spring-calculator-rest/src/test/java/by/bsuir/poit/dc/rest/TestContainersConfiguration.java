package by.bsuir.poit.dc.rest;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

/**
 * @author Paval Shlyk
 * @since 24/02/2024
 */
@TestConfiguration
public class TestContainersConfiguration {
    @Bean(initMethod = "start", destroyMethod = "stop")
//    @Bean
//    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
	return new PostgreSQLContainer<>("postgres:16");
    }

    @Bean
    public DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
	var config = new HikariConfig();
	config.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
	config.setUsername(postgreSQLContainer.getUsername());
	config.setPassword(postgreSQLContainer.getPassword());
	return new HikariDataSource(config);
    }
}
