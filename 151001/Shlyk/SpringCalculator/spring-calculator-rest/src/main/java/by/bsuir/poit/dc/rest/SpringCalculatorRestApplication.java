package by.bsuir.poit.dc.rest;

import by.bsuir.poit.dc.context.CatchThrowsBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@SpringBootApplication
public class SpringCalculatorRestApplication {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CatchThrowsBeanPostProcessor catchThrowsBeanPostProcessor() {
	return new CatchThrowsBeanPostProcessor();
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringCalculatorRestApplication.class, args);
    }
}
