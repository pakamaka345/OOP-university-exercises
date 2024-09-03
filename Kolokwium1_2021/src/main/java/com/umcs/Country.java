package com.umcs;

import com.umcs.exceptions.CountryNotFoundException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public abstract class Country {
    private final String name;
    private static String pathToConfirmed_cases;
    private static String pathToDeaths;
    public Country(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public static void setFiles(String pathToConfirmed_cases, String pathToDeaths) throws FileNotFoundException {
        File confirmed_cases = new File(pathToConfirmed_cases);
        File deaths = new File(pathToDeaths);
        if(!confirmed_cases.exists() || !confirmed_cases.canRead()){
            throw new FileNotFoundException(pathToConfirmed_cases);
        }
        if(!deaths.exists() || !deaths.canRead()){
            throw new FileNotFoundException(pathToDeaths);
        }
        Country.pathToConfirmed_cases = pathToConfirmed_cases;
        Country.pathToDeaths = pathToDeaths;
    }
    public static Country fromCsv(String countryName) throws CountryNotFoundException {
        Country country = null;
        List<Country> provinces;
        try(
                BufferedReader confirmed_cases = new BufferedReader(new FileReader(pathToConfirmed_cases));
                BufferedReader deaths = new BufferedReader(new FileReader(pathToDeaths))
                ){
            // Confirmed cases
            String firstLineOfConfirmed_cases = confirmed_cases.readLine();
            CountryColumns countryColumnsConfirmed_cases = getCountryColumns(firstLineOfConfirmed_cases, countryName);
            // Deaths
            String firstLineOfDeaths = deaths.readLine();
            CountryColumns countryColumnsDeaths = getCountryColumns(firstLineOfDeaths, countryName);
            String lineConfirmed_cases;
            String lineDeaths;
            if(countryColumnsConfirmed_cases.columnCount == 1 && countryColumnsDeaths.columnCount == 1){
                country = new CountryWithoutProvinces(countryName);
                confirmed_cases.readLine();
                deaths.readLine();
            }
            else{
                lineConfirmed_cases = confirmed_cases.readLine();
                deaths.readLine();
                String[] provinceNames = lineConfirmed_cases.split(";");
                provinces = new ArrayList<>();
                for(int i = countryColumnsConfirmed_cases.firstColumnIndex; i < countryColumnsConfirmed_cases.firstColumnIndex + countryColumnsConfirmed_cases.columnCount; i++){
                    provinces.add(new CountryWithoutProvinces(provinceNames[i]));
                }
                country = new CountryWithProvinces(countryName, provinces);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
            lineConfirmed_cases = confirmed_cases.readLine();
            lineDeaths = deaths.readLine();
            while(lineConfirmed_cases != null && lineDeaths != null){
                String[] confirmed_casesValues = lineConfirmed_cases.split(";");
                String[] deathsValues = lineDeaths.split(";");
                LocalDate date = LocalDate.parse(confirmed_casesValues[0], formatter);
                if(country instanceof CountryWithoutProvinces){
                    ((CountryWithoutProvinces) country).addDailyStatistic(date,
                            Math.abs(Integer.parseInt(confirmed_casesValues[countryColumnsConfirmed_cases.firstColumnIndex])),
                            Math.abs(Integer.parseInt(deathsValues[countryColumnsDeaths.firstColumnIndex])));
                }
                else{
                    for(int i = 0; i < ((CountryWithProvinces) country).getProvinces().size(); i++){
                        ((CountryWithoutProvinces) ((CountryWithProvinces) country).getProvinces().get(i)).addDailyStatistic(date,
                                Math.abs(Integer.parseInt(confirmed_casesValues[countryColumnsConfirmed_cases.firstColumnIndex + i])),
                                Math.abs(Integer.parseInt(deathsValues[countryColumnsDeaths.firstColumnIndex + i])));
                    }
                }
                lineConfirmed_cases = confirmed_cases.readLine();
                lineDeaths = deaths.readLine();
            }
        }catch (IOException e){
            System.err.println("Error while reading files");
        }
        return country;
    }
    public static List<Country> fromCsv(List<String> countryNames){
        List<Country> countries = new ArrayList<>();
        for(String countryName : countryNames){
            try {
                countries.add(fromCsv(countryName));
            }catch (CountryNotFoundException e){
                System.err.println(e.getMessage());
            }
        }
        return countries;
    }
    private static CountryColumns getCountryColumns(String firstLine, String countryName) throws CountryNotFoundException {
        String[] countryNames = firstLine.split(";");
        int firstColumnIndex = -1;
        int columnCount = 0;
        for(int i = 0; i < countryNames.length; i++){
            if(countryNames[i].equals(countryName)){
                firstColumnIndex = i;
                columnCount++;
                for(int j = i + 1; j < countryNames.length; j++){
                    if(countryNames[j].equals(countryNames[i])){
                        columnCount++;
                    }
                    else break;
                }
                break;
            }
        }
        if(firstColumnIndex == -1){
            throw new CountryNotFoundException(countryName);
        }
        return new CountryColumns(firstColumnIndex, columnCount);
    }
    public static List<Country> sortByDeaths(List<Country> countries, LocalDate startDate, LocalDate endDate){
        countries.sort((country1, country2) -> {
                int deathsCountry1 = IntStream.rangeClosed(0, (int) ChronoUnit.DAYS.between(startDate, endDate))
                        .mapToObj(startDate::plusDays)
                        .mapToInt(country1::getDeaths)
                        .sum();
                int deathsCountry2 = IntStream.rangeClosed(0, (int) ChronoUnit.DAYS.between(startDate, endDate))
                        .mapToObj(startDate::plusDays)
                        .mapToInt(country2::getDeaths)
                        .sum();
                return deathsCountry2 - deathsCountry1;
        });
        return countries;
    }
    public void saveToDataFile(String path){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))){
            writer.write("Date\tConfirmed cases\tDeaths\n");
            for(LocalDate date = LocalDate.of(2020, 1, 23); date.isBefore(LocalDate.of(2021, 4, 24)); date = date.plusDays(1)){
                writer.write(date.format(formatter) + "\t" + getConfirmedCases(date) + "\t" + getDeaths(date) + "\n");
            }
        }catch (IOException e){
            System.out.println("Error while writing to file");
        }
    }
    public abstract int getConfirmedCases(LocalDate date);
    public abstract int getDeaths(LocalDate date);

    private record CountryColumns(int firstColumnIndex, int columnCount) {
    }
}
