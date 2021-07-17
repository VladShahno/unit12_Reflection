package com.nixsolution.data;

import com.nixsolution.CSVTypes;
import com.nixsolution.annotation.CSVField;
import com.nixsolution.annotation.CSVHeader;

@CSVHeader
public class Person {

    @CSVField(index = 1)
    private String name;
    
    @CSVField(index = 2, type = CSVTypes.INTEGER)
    private Integer age;
    
    @CSVField(index = 3, type = CSVTypes.BOOL)
    private Boolean isUser;
}
