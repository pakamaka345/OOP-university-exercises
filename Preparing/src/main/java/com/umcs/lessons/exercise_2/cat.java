package com.umcs.lessons.exercise_2;

public class cat extends Animal{
    public cat(String name){
        super(name);
    }
    @Override
    public String sound(){
        return getName() + " says Meow!";
    }
}
