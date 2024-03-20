package by.bsuir.poit.dc.rest.context;

import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
public abstract class AbstractMethodHandlerBeanPostProcessor implements BeanPostProcessor {
    protected String prepareName(Method method) {
	return method.getName();
    }
}
