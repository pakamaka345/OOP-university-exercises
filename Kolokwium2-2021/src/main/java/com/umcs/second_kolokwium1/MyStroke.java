package com.umcs.second_kolokwium1;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyStroke {
    private double[] points = new double[4];
    private Color color;

    public MyStroke(double x1, double y1, double x2, double y2, Color color) {
        points[0] = x1;
        points[1] = y1;
        points[2] = x2;
        points[3] = y2;
        this.color = color;
    }
}
