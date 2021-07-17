package com.nixsolution;

import com.nixsolution.annotation.CSVField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class CSVInfo {
    final int index;
    final CSVTypes type;
    final Field field;
    final boolean quotes;
    final String format;

    CSVInfo(CSVField annotation, Field field) {
        this.index = annotation.index();
        this.type = annotation.type();
        this.quotes = annotation.quotes();
        this.format = annotation.format();
        this.field = field;
    }

    static List<CSVInfo> getAnnotatedFieldInfo(Class<?> csvClass) {
        List<CSVInfo> fields = new ArrayList<>();
        Field[] fieldArray = csvClass.getDeclaredFields();
        for (Field each : fieldArray) {
            CSVField annotation = each.getAnnotation(CSVField.class);
            if (annotation != null) {
                each.setAccessible(true);
                fields.add(new CSVInfo(annotation, each));
            }
        }
        return fields;
    }
}