package by.bsuir.poit.dc.rest.context;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Paval Shlyk
 * @since 05/03/2024
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public RestTemplate cassandraTemplate() {
	return new RestTemplateBuilder().
		   rootUri("localhost:24130/api/v1.0")
		   .build();
    }
}
