package by.bsuir.dc.rest_basics.services.validation;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class Validator{

    private final Map<Class<?>, EntityValidator> validators;

    @Around("execution(* by.bsuir.dc.rest_basics.services.common.AbstractService.create(..))")
    public Object validateCreate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object o = args[0];

        EntityValidator validator = validators.get(o.getClass());
        if (validator != null) {
            validator.validateCreate(o);
        }

        return joinPoint.proceed();
    }

    @Around("execution(* by.bsuir.dc.rest_basics.services.common.AbstractService.update(..))")
    public Object validateUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object o = args[0];

        EntityValidator validator = validators.get(o.getClass());
        if (validator != null) {
            validator.validateUpdate(o);
        }

        return joinPoint.proceed();
    }

}

