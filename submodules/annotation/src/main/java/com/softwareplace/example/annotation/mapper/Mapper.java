package com.softwareplace.example.annotation.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapper {

    /**
     * The target class that the mapper will generate
     * the mapping between the source class.
     */
    Class<?> value();

    /**
     * The priority is used to define the generation order of the mappers.
     */
    int priority() default 0;

    /**
     * In needed case to extend another mapper, set the class here.
     */
    Class<?> extendsTo() default Object.class;

    /**
     * In case the extendsTo is not in the same package, you can set the package here.
     */
    String extendsToPackage() default "";

    /**
     * Enables mapstruct component model that enable DI.
     */
    String componentModel() default "";

    /**
     * By setting this value to false, the implementation class will not be
     * generated because the generated interface with not be annotated with @Mapper.
     */
    boolean generateImpl() default true;
}
