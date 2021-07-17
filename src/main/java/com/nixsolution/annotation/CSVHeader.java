package com.nixsolution.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVHeader {
    boolean has_header() default false;
    String header() default "";
}
