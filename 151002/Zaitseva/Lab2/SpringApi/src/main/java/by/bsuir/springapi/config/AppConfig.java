package by.bsuir.springapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class AppConfig {
    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource1 = new DriverManagerDataSource();
        dataSource1.setDriverClassName("org.postgresql.Driver");
        dataSource1.setUrl("jdbc:postgresql://localhost:5433/postgres");
        dataSource1.setUsername("postgres");
        dataSource1.setPassword("postgres");
        return dataSource1;
    }
    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return  jdbcTemplate;
    }
}