package com.umcs;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DigitalClock extends Clock{
    Format format;
    public DigitalClock(Format format, City city){
        super(city);
        this.format = format;
    }
    public String toString(){
        if (format == Format.TIME_12){
            String[] time = super.toString().split(":");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.ENGLISH);
            LocalTime localTime = LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
            return localTime.format(formatter);
        }else {
            return super.toString();
        }
    }
    public enum Format {TIME_12, TIME_24}
}
