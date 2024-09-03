package com.umcs.lessons;

import java.util.Locale;

public class Ellipse implements Shape{
    private Vec2 center;
    private double radiusX;
    private double radiusY;
    public Ellipse(Vec2 centerY, double radiusX, double radiusY){
        //super(style);
        this.center = center;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }
    @Override
    public String toSvg(String parameters) {
        return String.format(Locale.ENGLISH, "<ellipse cx=\"%f\" cy=\"%f\" rx=\"%f\" ry=\"%f\" %s />",
                center.x, center.y, radiusX, radiusY, parameters);

    }
}
