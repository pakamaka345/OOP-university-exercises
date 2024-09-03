package com.umcs.lessons.exercise_1;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Person {
    private String name;
    private String surname;

    public Person(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    public String getNameWithSurname(){
        return name + " " + surname;
    }
}
