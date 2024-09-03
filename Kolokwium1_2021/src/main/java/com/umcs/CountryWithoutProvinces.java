package com.umcs;

import com.umcs.models.DailyStatistic;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CountryWithoutProvinces extends Country{
    private Map<LocalDate, DailyStatistic> dailyStatistics;
    public CountryWithoutProvinces(String name){
        super(name);
        this.dailyStatistics = new HashMap<>();
    }
    public void addDailyStatistic(LocalDate date, int infected, int deaths){
        dailyStatistics.put(date, new DailyStatistic(infected, deaths));
    }
    @Override
    public int getConfirmedCases(LocalDate date){
        return dailyStatistics.get(date).getConfirmed_cases();
    }
    @Override
    public int getDeaths(LocalDate date){
        return dailyStatistics.get(date).getDeaths();
    }
}
