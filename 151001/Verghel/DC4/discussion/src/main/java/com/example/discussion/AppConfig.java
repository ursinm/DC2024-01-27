package com.example.discussion;

import com.datastax.oss.driver.api.core.CqlSession;
import com.example.discussion.repository.MessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackageClasses = {MessageRepository.class})

public class AppConfig {

    /*
     * Use the standard Cassandra driver API to create a com.datastax.oss.driver.api.core.CqlSession instance.
     */
    public @Bean CqlSession session() {
        return CqlSession.builder().withKeyspace("distcomp").build();
    }
}