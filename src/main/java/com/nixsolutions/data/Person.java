package com.nixsolutions.data;

import com.nixsolutions.annotation.CSVField;

public class Person {

    @CSVField("id")
    private int id;

    @CSVField("name")
    private String name;

    @CSVField("isuser")
    private Boolean isUser;

    @CSVField("gender")
    private Gender gender;

    public enum Fields {
        ID(1, "id"),
        NAME(2, "name"),
        ISUSER(3, "isuser"),
        GENDER(4, "gender");

        private int fieldIndex;
        private String fieldsName;

        Fields(int fieldIndex, String fieldsName) {
            this.fieldIndex = fieldIndex;
            this.fieldsName = fieldsName;
        }

        public int getFieldIndex() {
            return fieldIndex;
        }

        public String getFieldsName() {
            return fieldsName;
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isUser=" + isUser +
                ", gender=" + gender +
                '}';
    }
}
