package com.umcs.exceptions;

public class CountryNotFoundException extends Exception{
    public CountryNotFoundException(String name){
        super("Country: " + name + " is not found");
    }
}
