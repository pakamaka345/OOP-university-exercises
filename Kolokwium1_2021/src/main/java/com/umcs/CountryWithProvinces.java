package com.umcs;

import java.time.LocalDate;
import java.util.List;

public class CountryWithProvinces extends Country{
    private List<Country> provinces;
    public CountryWithProvinces(String name, List<Country> provinces) {
        super(name);
        this.provinces = provinces;
    }
    public List<Country> getProvinces(){
        return provinces;
    }
    @Override
    public int getConfirmedCases(LocalDate date){
        return provinces.stream().mapToInt(province -> province.getConfirmedCases(date)).sum();
    }
    @Override
    public int getDeaths(LocalDate date){
        return provinces.stream().mapToInt(province -> province.getDeaths(date)).sum();
    }
}
