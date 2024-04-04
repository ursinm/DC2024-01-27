package by.bsuir.poit.dc.cassandra;

import by.bsuir.poit.dc.context.CatchThrowsBeanPostProcessor;
import com.ing.data.cassandra.jdbc.CassandraDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * @author Paval Shlyk
 * @since 24/02/2024
 */
@SpringBootApplication
@EnableCassandraRepositories
public class SpringCassandraApplication {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CatchThrowsBeanPostProcessor catchThrowBeanPostProcessor() {
	return new CatchThrowsBeanPostProcessor();
    }


    public static void main(String[] args) {
	SpringApplication.run(SpringCassandraApplication.class, args);
    }
}
