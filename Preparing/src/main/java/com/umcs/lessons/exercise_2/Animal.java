package com.umcs.lessons.exercise_2;

public abstract class Animal {
    private String name;
    public Animal(String name){
        this.name = name;
    }
    protected String getName(){
        return name;
    }
    public abstract String sound();
}
