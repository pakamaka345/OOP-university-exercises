package com.umcs;

import java.time.LocalTime;
import java.util.Locale;

public class HourHand extends ClockHand{
    private int hour;
    @Override
    public void setTime(LocalTime time){
        this.hour = time.getMinute() * 30;
    }
    @Override
    public String toSvg(){
        return String.format(Locale.ENGLISH, "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-50\" stroke=\"black\" stroke-width=\"4\" transform=\"rotate(180)\" />", hour);
    }
}
