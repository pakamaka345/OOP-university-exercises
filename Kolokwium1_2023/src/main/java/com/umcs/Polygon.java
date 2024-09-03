package com.umcs;

import com.umcs.model.Point;

import java.util.List;

public class Polygon {
    private List<Point> points;
    public Polygon(List<Point> points){
        this.points = points;
    }
    public List<Point> getPoints(){
        return points;
    }
    public boolean inside(Point point){
        int counter = 0;
        for (int i = 0; i < points.size() - 1; i++){
            Point pa = points.get(i);
            Point pb = points.get(i + 1);
            if (pa.y() > pb.y()){
                Point tmp = pa;
                pa = pb;
                pb = tmp;
            }
            if (pa.y() < point.y() && point.y() < pb.y()){
                double d = pb.x() - pa.x();
                double x;
                if (d == 0){
                    x = pa.x();
                }else {
                    double a = (pb.y() - pa.y()) / d;
                    double b = pa.y() - a * pa.x();
                    x = (point.y() - b) / a;
                }
                if (x < point.x()){
                    counter++;
                }
            }
        }
        return counter % 2 != 0;
    }
}
