package by.bsuir.poit.dc.rest.context;

import by.bsuir.poit.dc.rest.api.dto.response.DeleteDto;
import lombok.Builder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AdviceName;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Paval Shlyk
 * @since 26/02/2024
 */
@Aspect
//@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class DtoHttpResponseBeanPostProcessor {
    @Around(value = "@annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
			"&& execution(public Object *(..))")
    public Object handleDeleteDto(ProceedingJoinPoint joinPoint) throws Throwable {
	Object object = joinPoint.proceed();
	if (!(object instanceof DeleteDto dto)) {
	    return object;
	}
	var status = dto.isDeleted()
			 ? HttpStatus.NO_CONTENT
			 : HttpStatus.NOT_FOUND;
	return ResponseEntity.status(status).build();
    }
//    private final Map<String, Set<String>> beanMethodsMap = new HashMap<>();
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//	Method[] methods = bean.getClass().getDeclaredMethods();
//	var set = new HashSet<String>();
//	for (Method method : methods) {
//	    if (method.isAnnotationPresent(DeleteMapping.class) && method.getReturnType().isAssignableFrom(DeleteDto.class)) {
//		String name = super.prepareName(method);
//		set.add(name);
//	    }
//	}
//	if (!set.isEmpty()) {
//	    beanMethodsMap.put(beanName, set);
//	}
//	return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//	if (!beanMethodsMap.containsKey(beanName)) {
//	    return bean;
//	}
//	Class<?> clazz = bean.getClass();
//	Set<String> set = beanMethodsMap.get(beanName);
//	MethodMapRecord handler = MethodMapRecord.builder()
//				      .bean(bean)
//				      .nameConverter(super::prepareName)
//				      .methods(set)
//				      .build();
//	return Proxy.newProxyInstance(
//	    clazz.getClassLoader(),
//	    clazz.getInterfaces(),
//	    handler
//	);
//    }
//
//
//    @Builder
//    private record MethodMapRecord(
//	Object bean,
//	Function<Method, String> nameConverter,
//	Set<String> methods) implements InvocationHandler {
//
//	@Override
//	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//	    String methodName = nameConverter.apply(method);
//	    if (!methods.contains(methodName)) {
//		return method.invoke(bean, args);
//	    }
//	    DeleteDto dto = (DeleteDto) method.invoke(args);
//	    return handleDeleteDto(dto);
//	}
//
//	public Object handleDeleteDto(DeleteDto dto) {
//	    var status = dto.isDeleted()
//			     ? HttpStatus.NO_CONTENT
//			     : HttpStatus.NOT_FOUND;
//	    return ResponseEntity.status(status).build();
//	}
//    }
}
