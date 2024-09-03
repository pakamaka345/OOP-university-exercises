package com.umcs.lessons;

public class AmbiguousPersonException extends Exception{
    public AmbiguousPersonException(Person person){
        super("Ambiguous name " + person.getName());
    }
}
