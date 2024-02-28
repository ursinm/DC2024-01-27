package by.bsuir.dc.rest_basics.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MyTestConfiguration {

    @Bean
    public String uriBase() {
        return "http://localhost:24110/api/v1.0";
    }

}
