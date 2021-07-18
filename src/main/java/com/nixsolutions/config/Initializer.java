package com.nixsolutions.config;

import com.nixsolutions.annotation.CSVField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Initializer<T> {

    public List<T> Initialize(Class<T> objectClass, CSVParser parser) {
        ArrayList<T> arrayList = new ArrayList<>();
        try {
            for (int i = 1; i <= parser.getSize(); i++) {
                T declaredInstance = (T) objectClass.getDeclaredConstructor().newInstance();
                Field[] declaredFields = objectClass.getDeclaredFields();
                for (Field field :
                        declaredFields) {
                    CSVField csvField = field.getAnnotation(CSVField.class);
                    if (csvField != null) {
                        String headerString = csvField.value();
                        castObject(field, parser.get(i, headerString), declaredInstance);
                    }
                }
                arrayList.add(declaredInstance);
            }
            return arrayList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Can't set filed ");
        }
    }

    private void castObject(Field field, String value, T obj) {

        field.setAccessible(true);
        Class fieldsType = field.getType();
        try {
            if (fieldsType.isAssignableFrom(long.class)) {
                field.set(obj, Long.parseLong(value));
            } else if (fieldsType.isAssignableFrom(int.class)) {
                field.set(obj, Integer.parseInt(value));
            } else if (fieldsType.isAssignableFrom(double.class)) {
                field.set(obj, Double.parseDouble(value));
            } else if (fieldsType.isAssignableFrom(float.class)) {
                field.set(obj, Float.parseFloat(value));
            } else if (fieldsType.isAssignableFrom(byte.class)) {
                field.set(obj, Byte.parseByte(value));
            } else if (fieldsType.isAssignableFrom(char.class)) {
                field.set(obj, value.charAt(0));
            }  else if (fieldsType.isEnum()) {
                field.set(obj, Enum.valueOf((Class<Enum>) fieldsType, value));
            } else if (fieldsType.isAssignableFrom(Boolean.class)) {
                field.set(obj, Boolean.parseBoolean(value));
            } else {
                field.set(obj, value);
            }
        } catch (IllegalAccessException e) {
            System.out.println("Can't access the field " + field.getName());
        }
    }
}