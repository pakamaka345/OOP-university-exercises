package com.umcs;

import java.time.LocalTime;

public abstract class ClockHand {
    public abstract void setTime(LocalTime time);
    public abstract String toSvg();
}
