package by.bsuir.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableJpaAuditing
public class PublisherApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(PublisherApplication.class, args);
    }
}
