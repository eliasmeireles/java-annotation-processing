package com.softwareplace.example.annotation.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapper {

    Class<?> value();

    int priority() default 0;

    Class<?> extendsTo() default Object.class;

    String extendsToPackage() default "";

    String componentModel() default "";

    boolean generateImpl() default true;
}
