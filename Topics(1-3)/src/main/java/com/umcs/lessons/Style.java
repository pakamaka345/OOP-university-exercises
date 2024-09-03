package com.umcs.lessons;

import java.util.Locale;

public class Style {
    final public String fillColor;
    final public String strokeColor;
    final public double strokeWidth;
    public Style(String fillColor, String strokeColor, double strokeWidth){
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }
    public String toSvg(){
        return String.format(Locale.ENGLISH, "fill:%s;stroke:%s;stroke-width:%f", fillColor, strokeColor, strokeWidth);
    }
}
