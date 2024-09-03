package com.umcs;

import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start, end;
        processor img = new processor();
        img.loadImage("src/main/resources/image.jpg");
        start = System.currentTimeMillis();
        img.increaseBrightness(40);
        end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start));

        img.loadImage("src/main/resources/image.jpg");

        start = System.currentTimeMillis();
        img.increaseBrightnessMulti(40);
        end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start));
        img.saveImage("src/main/resources/test2.jpg");


    }

}