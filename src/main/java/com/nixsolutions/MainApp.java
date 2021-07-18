package com.nixsolutions;

import com.nixsolutions.config.CSVParser;
import com.nixsolutions.config.Initializer;
import com.nixsolutions.data.Person;

public class MainApp {
    public static void main(String[] args) {

        CSVParser csvParser = new CSVParser("src/csv_test.csv");
        csvParser.parse();

        Initializer<Person> personInitializer = new Initializer<>();

        personInitializer.Initialize(Person.class, csvParser).forEach(System.out::println);

        System.out.println(csvParser.get(1, Person.Fields.ID.getFieldsName()));
        System.out.println(csvParser.get(1, Person.Fields.NAME.getFieldsName()));
        System.out.println(csvParser.get(1, Person.Fields.ISUSER.getFieldsName()));
        System.out.println(csvParser.get(1, Person.Fields.GENDER.getFieldsName()));
    }
}

