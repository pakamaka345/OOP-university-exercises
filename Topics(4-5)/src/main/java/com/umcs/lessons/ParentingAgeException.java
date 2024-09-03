package com.umcs.lessons;

public class ParentingAgeException extends Exception{
    public ParentingAgeException(Person person, Person child){
        super("Parent " + person.getName() + " is too young to have child " + child.getName() + "!");
    }
}
