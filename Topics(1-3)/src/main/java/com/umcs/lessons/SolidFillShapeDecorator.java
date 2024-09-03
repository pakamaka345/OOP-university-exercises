package com.umcs.lessons;

import java.util.Locale;

public class SolidFillShapeDecorator extends ShapeDecorator{
    private String color;
    SolidFillShapeDecorator(Shape shape, String color)
    {
        super(shape);
        this.color = color;
    }
    @Override
    public String toSvg(String parameters){
       return decoratedShape.toSvg(String.format(Locale.ENGLISH, "fill=\"%s\" %s", color, parameters));
    }
}
