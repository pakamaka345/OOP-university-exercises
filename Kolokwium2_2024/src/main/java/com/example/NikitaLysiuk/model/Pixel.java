package com.example.NikitaLysiuk.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pixel {
    private Token token;
    private int x;
    private int y;
    private String color;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
