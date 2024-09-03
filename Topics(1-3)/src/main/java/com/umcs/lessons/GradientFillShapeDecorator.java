package com.umcs.lessons;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GradientFillShapeDecorator extends ShapeDecorator{
    private static int index = 0;
    private List<Stop> stops;
    public GradientFillShapeDecorator(Shape shape, List<Stop> stops){
        super(shape);
        this.stops = stops;
        String gradient = String.format(Locale.ENGLISH,
                "<linearGradient id=\"grad%d\">\n", index);
        for (Stop stop : stops){
            gradient += String.format(Locale.ENGLISH,
                    "\t\t\t\t\t<stop offset=\"%f\" style=\"stop-color:%s\" />\n", stop.offset, stop.color);
        }
        gradient += String.format(Locale.ENGLISH,
                "\t\t\t\t</linearGradient>\n");
        SvgScene.getInstance().addDef(gradient);
        index++;
    }
    public String toSvg(String parameters){
        return decoratedShape.toSvg(String.format(Locale.ENGLISH, "fill=\"url(#grad%d)\" %s", index - 1, parameters));
    }
    public static class Builder{
        private List<Stop> stops = new ArrayList<>();
        private Shape shape;
        public Builder shape(Shape shape){
            this.shape = shape;
            return this;
        }
        public Builder addStop(float offset, String color){
            stops.add(new Stop(offset, color));
            return this;
        }
        public GradientFillShapeDecorator build(){
            return new GradientFillShapeDecorator(shape, stops);
        }
    }
    private static class Stop{
        float offset;
        String color;
        public Stop(float offset, String color){
            this.color = color;
            this.offset = offset;
        }
    }
}
