package com.nixsolution.annotation;

import com.nixsolution.CSVTypes;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVField {
    int index();
    CSVTypes type() default CSVTypes.STRING;
    boolean quotes() default false;
    String format() default "";
}
