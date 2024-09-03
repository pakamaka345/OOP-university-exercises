package com.umcs;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Getter
@Setter
public class City {
    private String capital;
    private double timeZone;
    private String widthGeographical;
    private String lengthGeographical;
    public City(String capital, double timeZone, String widthGeographical, String lengthGeographical) {
        this.capital = capital;
        this.timeZone = timeZone;
        this.widthGeographical = widthGeographical;
        this.lengthGeographical = lengthGeographical;
    }
    private static City parseLine(String line){
        String[] parts = line.split(",");
        double timeZone = Double.parseDouble(parts[1].startsWith("+") ? parts[1].substring(1) : parts[1]);
        return new City(parts[0].trim(), timeZone, parts[2].trim(), parts[3].trim());
    }
    public static Map<String, City> parseFile(String path){
        Map<String, City> cities = new HashMap<>();
        try (Stream<String> fileLines = Files.lines(Path.of(path))){
            fileLines.skip(1).forEach(line -> {
                City city = parseLine(line);
                cities.put(city.getCapital(), city);
            });
        }catch (IOException e){
            System.err.println("Error while reading file: " + e.getMessage());
        }
        return cities;
    }
    public LocalTime localMeanTime(LocalTime time){
        String[] parts = lengthGeographical.split(" ");
        double longitude = Double.parseDouble(parts[0]);
        long minuteDifference = (long) (longitude * 4);
        long secondDifference = (long) ((longitude * 4 - minuteDifference) * 60);
        if (parts[1].equals("W")){
            minuteDifference *= -1;
            secondDifference *= -1;
        }
        return time.plusMinutes(minuteDifference).plusSeconds(secondDifference);
    }
    public static long worstTimezoneFit(City city){
        double time = (180 - city.getTimeZone() * 15) % 24;
        LocalTime timeZone = LocalTime.of((int) time, (int) ((time - (int) time) * 60));
        LocalTime meanTime = city.localMeanTime(timeZone);
        return Math.abs(Duration.between(timeZone, meanTime).getSeconds());
    }
    public static void generateAnalogClocksSvg(List<City> cityList, AnalogClock analogClock){
        String path = "src/main/resources" + analogClock.toString();
        try{
            if (!Files.exists(Path.of(path)))
                Files.createDirectories(Path.of(path));
            for (City city : cityList){
                path = path + city.getCapital() + ".svg";
                analogClock.setCurrentTime();
                analogClock.toSvg(path);
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }
    @Override
    public String toString() {
        return "City{" +
                "capital='" + capital + '\'' +
                ", timeZone=" + timeZone +
                ", widthGeographical='" + widthGeographical + '\'' +
                ", lengthGeographical='" + lengthGeographical + '\'' +
                '}';
    }
}
