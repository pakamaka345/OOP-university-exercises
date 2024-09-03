package com.umcs;

import lombok.Getter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Clock {
    @Getter
    private LocalTime time;
    private City city;
    public Clock(City city){
        this.city = city;
    }

    public void setCurrentTime(){
        this.time = LocalTime.now();
    }

    public void setTime(int hour, int minute, int second) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be between 0 and 23. Provided: " + hour);
        }
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Minute must be between 0 and 59. Provided: " + minute);
        }
        if (second < 0 || second > 59) {
            throw new IllegalArgumentException("Second must be between 0 and 59. Provided: " + second);
        }
        this.time = LocalTime.of(hour, minute, second);
    }
    public void setCity(City city){
        double oldTimeZone = this.city.getTimeZone();
        double newTimeZone = city.getTimeZone();
        double difference = newTimeZone - oldTimeZone;
        this.time = this.time.plusHours((long) difference);
        this.city = city;
    }
    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return this.time.format(formatter);
    }
}
