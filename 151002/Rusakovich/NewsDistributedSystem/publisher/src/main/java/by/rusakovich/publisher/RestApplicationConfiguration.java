package by.rusakovich.publisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestApplicationConfiguration {

    @Bean
    public RestClient restTemplate() {
        return RestClient.create();
    }
}