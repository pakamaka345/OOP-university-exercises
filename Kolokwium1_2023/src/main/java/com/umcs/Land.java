package com.umcs;

import com.umcs.model.Point;

import java.util.ArrayList;
import java.util.List;

public class Land extends Polygon{
    private List<City> cities;
    public Land(List<Point> points){
        super(points);
        cities = new ArrayList<>();
    }
    public void addCity(City city){
        try{
            centerOnLand(city);
            city.setPort(this);
            cities.add(city);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
    public int numberOfCities(){
        return cities.size();
    }
    private void centerOnLand(City c) throws RuntimeException{
        if (!this.inside(c.center)){
            throw new RuntimeException(c.getName() + " is outside the land");
        }
    }
}
