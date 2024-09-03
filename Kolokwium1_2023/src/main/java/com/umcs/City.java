package com.umcs;

import com.umcs.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class City extends Polygon{
    public final Point center;
    private String name;
    private boolean isPort = false;
    Set<Resource.Type> resources;
    public City(Point center, String name, double lengthWall){
        super(calculateCityCorners(center, lengthWall));
        this.center = center;
        this.name = name;
    }
    public String getName(){
        return name;
    }
    private static List<Point> calculateCityCorners(Point center, double length){
        List<Point> corners = new ArrayList<>();
        double halfLength = length / 2;
        corners.add(new Point(center.x() - halfLength, center.y() - halfLength));
        corners.add(new Point(center.x() + halfLength, center.y() - halfLength));
        corners.add(new Point(center.x() + halfLength, center.y() + halfLength));
        corners.add(new Point(center.x() - halfLength, center.y() + halfLength));
        return corners;
    }
    public void setPort(Land land){
        for (Point p : this.getPoints()){
            if (!land.inside(p)){
                isPort = true;
                break;
            }
        }
        isPort = false;
    }
    void addResourcesInRange(List<Resource> resources, double range){
        for (Resource r : resources){
            if (range <= Math.hypot(this.center.x() - r.point.x(), this.center.y() - r.point.y())){
                if (!r.type.equals(Resource.Type.Fish)){
                    this.resources.add(r.type);
                }else {
                    if (this.isPort){
                        this.resources.add(r.type);
                    }
                }
            }
        }
    }
}
