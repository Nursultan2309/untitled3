package org.example;


import app.jackychu.api.simplegoogletranslate.Language;
import app.jackychu.api.simplegoogletranslate.SimpleGoogleTranslate;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException, InterruptedException {
        SimpleGoogleTranslate translate = new SimpleGoogleTranslate();
        CSVReader cities = new CSVReader(new FileReader("C:\\Users\\Nurs\\IdeaProjects\\untitled3\\src\\main\\java\\org\\example\\cities.csv"));

        ICSVWriter entity = new CSVWriterBuilder(new FileWriter("C:\\Users\\Nurs\\IdeaProjects\\untitled3\\src\\main\\java\\org\\example\\jany.csv"))
                .withSeparator(';')
                .build();

        ICSVWriter ru = new CSVWriterBuilder(new FileWriter("C:\\Users\\Nurs\\IdeaProjects\\untitled3\\src\\main\\java\\org\\example\\entity.csv"))
                .withSeparator(';')
                .build();

        String[] line;
//        "100001";"";"Kabul";"";"";"";"";"Kabul";"Kabul";"";"";"1"
        List<String[]> entityLine = new ArrayList<>();
        List<String[]> russianLine = new ArrayList<>();
        int count = 100000;
        Connection connection = getConnection();
        while ((line = cities.readNext()) != null) {
            line = new String[]{
                    count++ + "","",
                    line[1].toUpperCase(),"","","","",
                    line[1].toUpperCase(),
                    line[1].toUpperCase(),"","",
                    getCountryIdByName(line[6], connection)
            };
//            russianLine.add(new String[] {line[1].toUpperCase(), translate.doTranslate(Language.en, Language.ru, "hello world")});
            entityLine.add(line);
        }

        entity.writeAll(entityLine);
        ru.writeAll(russianLine);
    }

    public static String getCountryIdByName(String name, Connection connection) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(String.format("select import_id from base_country where alpha2code like '%s'", name.toUpperCase()));
            if(resultSet.next()) {
                return resultSet.getInt("import_id") + "";
            }
            return name;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getcitiesId(){
        List<Integer> citiesId = new ArrayList<Integer>();
        for (int i = 100000; i <142256 ; i++) {
            citiesId.add(i);
        }
        return 0;
    }

    public static Connection getConnection() {
        final String USER = "postgres";
        final String PASS = "postgres";
        final String DB_URL = "jdbc:postgresql://localhost:5432/sanarip2";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            return connection;
        } catch (SQLException e) {
        }
        return null;
    }
}