package org.mochizuki.bot.service.Annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Plugin {
    Pattern ID_PATTERN = Pattern.compile("[a-z][a-z0-9-_]{0,63}");

    String id();

    String name() default "";

    String version() default "";

    String description() default "";

    String[] authors() default {};
}
