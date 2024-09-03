package com.umcs;

import java.util.Arrays;

public class DeathCauseStatistic {
    private String ICD_10;
    private int[] deaths;
    public DeathCauseStatistic(String ICD_10, int[] deaths){
        this.ICD_10 = ICD_10;
        this.deaths = deaths;
    }
    public String getICD_10(){
        return ICD_10;
    }
    public static DeathCauseStatistic fromCsvLine(String line){
        String[] lines = line.split(",");
        String ICD_10 = lines[0].trim();
        int[] deaths = Arrays.stream(lines)
                .skip(2)
                .mapToInt(p -> p.equals("-") ? 0 : Integer.parseInt(p))
                .toArray();
        return new DeathCauseStatistic(ICD_10, deaths);
    }
    public AgeBracketDeaths getAgeBracketDeath(int age){
        //String ages = "0 – 4,5 - 9,10 - 14,15 - 19,20 - 24,25 - 29,30 - 34,35 - 39,40 - 44,45 - 49,50 - 54,55 - 59,60 - 64,65 - 69,70 - 74,75 - 79,80 - 84,85 - 89,90 - 94,95";
        int agesIndex = age / 5;
        int young = agesIndex * 5;
        int old = young + 4;
        if (age >= 95){
            agesIndex = deaths.length - 1;
            old = Integer.MAX_VALUE;
        }
        return new AgeBracketDeaths(young, old, deaths[agesIndex]);
    }
    @Override
    public String toString() {
        return "DeathCauseStatistic{" +
                "ICD_10='" + ICD_10 + '\'' +
                ", deaths=" + Arrays.toString(deaths) +
                '}';
    }
    public record AgeBracketDeaths(int young, int old, int deathCount) {}
}
