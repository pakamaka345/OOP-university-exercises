package com.umcs.models;

public class DailyStatistic {
    private int infected;
    private int deaths;
    public DailyStatistic(int infected, int deaths){
        this.infected = infected;
        this.deaths = deaths;
    }
    public int getConfirmed_cases(){
        return infected;
    }
    public int getDeaths(){
        return deaths;
    }
}
