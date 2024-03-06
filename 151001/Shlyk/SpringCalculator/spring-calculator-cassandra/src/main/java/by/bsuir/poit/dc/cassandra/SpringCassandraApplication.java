package by.bsuir.poit.dc.cassandra;

import by.bsuir.poit.dc.context.CatchThrowsBeanPostProcessor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author Paval Shlyk
 * @since 24/02/2024
 */
@SpringBootApplication
public class SpringCassandraApplication {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CatchThrowsBeanPostProcessor catchThrowBeanPostProcessor() {
	return new CatchThrowsBeanPostProcessor();
    }
}
