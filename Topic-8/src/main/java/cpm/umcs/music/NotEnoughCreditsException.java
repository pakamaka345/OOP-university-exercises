package cpm.umcs.music;

public class NotEnoughCreditsException extends Exception{
    public NotEnoughCreditsException(String message){
        super(message);
    }
}
