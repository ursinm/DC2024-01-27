package discussion;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CassandraConfiguration {
    public @Bean CqlSession cqlSession() {
        return CqlSession.builder().withKeyspace("distcomp").build();
    }
}