package by.bsuir.poit.dc.rest.context;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * @author Paval Shlyk
 * @since 09/04/2024
 */
public class NewsLabelKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
	return null;
    }
}
