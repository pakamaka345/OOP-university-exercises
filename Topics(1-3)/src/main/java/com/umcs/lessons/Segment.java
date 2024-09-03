package com.umcs.lessons;

import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Setter
@Getter
public class Segment {
    private Vec2 p1;
    private Vec2 p2;
    public Segment(Vec2 p1, Vec2 p2){
        this.p1 = p1;
        this.p2 = p2;
    }
    public double length(){
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }
    public String toSvg(){
        return String.format(Locale.ENGLISH, "<line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" />", p1.x, p1.y, p2.x, p2.y);
    }
    public static Segment[] perpendicural(Segment segment, Vec2 vec2){
        double a;
        a = (segment.p1.y - segment.p2.y) / (segment.p1.x - segment.p2.x);
        double b;
        a=-1/a;
        b= vec2.y-a* vec2.x;

        double x0 = vec2.x;
        double y0 = vec2.y;
        double r = segment.length();

        double root = Math.sqrt(-y0*y0+(2*a*x0+2*b)*y0-a*a*x0*x0-2*a*b*x0+(a*a+1)*r*r-b*b);
        double x1 = -(root-a*y0-x0+a*b)/(a*a+1);
        double y1 = -(a*root-a*a*y0-a*x0-b)/(a*a+1);
        double x2 = (root+a*y0+x0-a*b)/(a*a+1);
        double y2 = (a*root+a*a*y0+a*x0+b)/(a*a+1);

        return new Segment[]{new Segment(vec2, new Vec2(x2, y2)), new Segment(vec2, new Vec2(x1, y1))};
    }
    public static Segment[] perpendicural(Segment segment, Vec2 vec2, double r) {
        double a;
        a = (segment.p1.y - segment.p2.y) / (segment.p1.x - segment.p2.x);
        double b;
        a=-1/a;
        b= vec2.y-a* vec2.x;

        double x0 = vec2.x;
        double y0 = vec2.y;

        double root = Math.sqrt(-y0*y0+(2*a*x0+2*b)*y0-a*a*x0*x0-2*a*b*x0+(a*a+1)*r*r-b*b);
        double x1 = -(root-a*y0-x0+a*b)/(a*a+1);
        double y1 = -(a*root-a*a*y0-a*x0-b)/(a*a+1);
        double x2 = (root+a*y0+x0-a*b)/(a*a+1);
        double y2 = (a*root+a*a*y0+a*x0+b)/(a*a+1);

        return new Segment[]{new Segment(vec2, new Vec2(x2, y2)), new Segment(vec2, new Vec2(x1, y1))};
    }
}
