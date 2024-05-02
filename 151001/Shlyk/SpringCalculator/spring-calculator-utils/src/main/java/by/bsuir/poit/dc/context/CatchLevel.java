package by.bsuir.poit.dc.context;

/**
 * @author Paval Shlyk
 * @since 18/02/2024
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface CatchLevel {
    Class<? extends Throwable> value();
}
