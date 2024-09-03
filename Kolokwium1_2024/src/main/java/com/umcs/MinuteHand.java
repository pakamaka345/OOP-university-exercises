package com.umcs;

import java.time.LocalTime;
import java.util.Locale;

public class MinuteHand extends ClockHand{
    private int minute;
    @Override
    public void setTime(LocalTime time){
        this.minute = time.getMinute() * 6;
    }
    @Override
    public String toSvg(){
        return String.format(Locale.ENGLISH, "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-70\" stroke=\"black\" stroke-width=\"2\" transform=\"rotate(%d)\" />", minute);
    }
}
