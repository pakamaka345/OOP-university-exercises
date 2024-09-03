package com.umcs.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rectangle {
    private int x, y, width, height;
    private String color;

    public Rectangle(int x, int y, int width, int height, String color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
}
