package com.umcs.lessons;

public class NegativeLifespanExceprion extends Exception{
    NegativeLifespanExceprion(Person person){
        super(person.getName() + "died before birth date" + person.getBirthDate() + " - " + person.getDeathDate());
    }
}
