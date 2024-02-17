package by.bsuir.poit.dc.rest;

import java.lang.annotation.RetentionPolicy;

/**
 * @author Paval Shlyk
 * @since 12/02/2024
 */
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
public @interface CatchThrows {
    Class<? extends Throwable> value() default java.lang.Throwable.class;

    /**
     * If named method throws exception, it will be suppressed by implementation
     *
     * @return the name of handler for given exception
     */
    String call();

    //the arguments of origin method
    String[] args() default {};
}
