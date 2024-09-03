package com.umcs;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        DigitalClock clock_12 = new DigitalClock(DigitalClock.Format.TIME_12);
//        DigitalClock clock_24 = new DigitalClock(DigitalClock.Format.TIME_24);
//        clock_12.setTime(14, 45, 30);
//        clock_24.setTime(14, 45, 30);
//        System.out.println(clock_12);
//        System.out.println(clock_24);

        Map<String, City> cityMap = City.parseFile("src/main/resources/strefy.csv");
        for(Map.Entry<String, City> entry : cityMap.entrySet()){
            System.out.println(entry.getKey() + " -> " + entry.getValue().toString());
        }

        System.out.println("--------------------------------------------------------\n");
        City city = cityMap.get("Warszawa");
        DigitalClock clock = new DigitalClock(DigitalClock.Format.TIME_12, city);
        clock.setTime(23, 45, 30);
        System.out.println(clock);
        clock.setCity(cityMap.get("Kijów"));
        System.out.println(clock);
        City lublin = cityMap.get("Lublin");
        System.out.println(lublin.localMeanTime(LocalTime.of(12,00,00)));

        System.out.println("--------------------------------------------------------\n");
        for(Map.Entry<String, City> entry : cityMap.entrySet()){
            System.out.println(entry.getKey() + " -> " + entry.getValue().toString());
        }
        System.out.println("--------------------------------------------------------\n");
        cityMap.entrySet().stream().sorted(Comparator.comparingLong(entry -> City.worstTimezoneFit(entry.getValue())))
                .forEach(entry -> System.out.println(entry.getKey() + " -> " + entry.getValue().toString()));
    }
}