package by.bsuir.discussion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableCassandraRepositories(basePackages = "by.bsuir.discussion.dao")
public class DiscussionApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(DiscussionApplication.class, args);
    }
}