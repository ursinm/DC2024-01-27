package by.bsuir.dc.rest_basics.configuration;

import by.bsuir.dc.rest_basics.author.TestAuthorDao;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MyTestConfiguration {

    @Bean
    @Primary
    public TestAuthorDao testAuthorDao() {
        return new TestAuthorDao();
    }

}
