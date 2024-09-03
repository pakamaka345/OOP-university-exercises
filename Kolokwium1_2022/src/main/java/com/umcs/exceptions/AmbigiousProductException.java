package com.umcs.exceptions;

import java.util.List;

public class AmbigiousProductException extends Exception{
    public AmbigiousProductException(List<String> string){
        super(string.toString());
    }
}
