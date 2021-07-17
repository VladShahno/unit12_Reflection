package com.nixsolution;

import com.nixsolution.annotation.CSVHeader;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVReader<T> {

    private Class csvClass;
    private List<CSVInfo> fields;

    public CSVReader(Class csvClass) {
        this.csvClass = csvClass;
        fields = CSVInfo.getAnnotatedFieldInfo(csvClass);
    }


    @SuppressWarnings("unchecked")
    private T getObjectFromCSV(String line) {
        try {
            String[] split = CSVParser.CSVSplit(line);
            Object instance;
            try {
                instance = csvClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            }
            for (CSVInfo each : fields) {
                if (each.index < 0 || each.index >= split.length) {
                    System.out.println("Incorrect CSV entry for line:");
                    System.out.println(line);
                    System.out.println("Ignoring line");
                    return null;
                }
                String temp = split[each.index];
                if (!temp.isEmpty()) {
                    try {
                        switch (each.type) {
                            case INTEGER:
                                int t = Integer.parseInt(temp);
                                each.field.set(instance, t);
                                break;
                            case FLOAT:
                                float f = Float.parseFloat(temp);
                                each.field.set(instance, f);
                                break;
                            case DOUBLE:
                                double d = Double.parseDouble(temp);
                                each.field.set(instance, d);
                                break;
                            case DATE:
                                SimpleDateFormat format = new SimpleDateFormat(each.format);
                                Date date = format.parse(temp);
                                each.field.set(instance, date);
                                break;
                            case BOOL:
                                boolean b = Boolean.parseBoolean(temp);
                                each.field.set(instance, b);
                                break;
                            case STRING:
                                each.field.set(instance, temp);
                                break;
                        }
                    }
                    catch (NumberFormatException nfe) {
                        System.out.println("Incorrect CSV entry for line: Number Format exception");
                        System.out.println(line);
                        System.out.println("Ignoring line");
                        return null;
                    }
                    catch (ParseException pe) {
                        System.out.println("Incorrect CSV entry for line: Problem parsing Date");
                        System.out.println(line);
                        System.out.println("Ignoring line");
                        return null;
                    }
                }
            }
            return (T) instance;
        } catch ( IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> readListFromCSV(String filename) {
        List<T> results = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            results = readListFromCSV(br);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return results;
    }

    public List<T> readListFromCSVURL(String url) {
        List<T> results = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()))){
            results = readListFromCSV(br);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return results;
    }

    private List<T> readListFromCSV(BufferedReader br) throws IOException{

        List<T> results = new ArrayList<>();

        String input;
        if (csvClass.isAnnotationPresent(CSVHeader.class)) {
            CSVHeader header = (CSVHeader) csvClass.getAnnotation(CSVHeader.class);
            if (header.has_header()) {
                // ignore header line
                br.readLine();
            }
        }
        while ((input = br.readLine()) != null) {
            T t = null;
            t = getObjectFromCSV(input);
            if (t != null) {
                results.add(t);
            }
        }
        return results;
    }
}