package com.umcs;


import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try{
            Country.setFiles("confirmed_cases.csv", "deaths.csv");
        }catch (FileNotFoundException e){
            System.err.println("File not found: " + e.getMessage());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = LocalDate.parse("6/23/2020", formatter);
        List<String> countryNames = List.of("Poland", "Germany", "Australia");
        List<Country> countries = Country.fromCsv(countryNames);
        countries.forEach(country -> {
            System.out.println(country.getName());
            System.out.println("Confirmed cases: " + country.getConfirmedCases(date));
            System.out.println("Deaths: " + country.getDeaths(date));
        });
        for(Country country : countries){
            country.saveToDataFile("src/main/resources/" + country.getName() + ".csv");
        }
    }
}