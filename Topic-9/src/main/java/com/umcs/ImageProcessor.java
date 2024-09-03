package com.umcs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ImageProcessor {
    private BufferedImage image;
    public void loadImage(String path) {
        try {
            File file = new File(path);
            image = ImageIO.read(file);
        } catch (IOException e){
            System.err.println("Error while loading image: " + e.getMessage());
        }
    }
    public void saveImage(String path) {
        try {
            File file = new File(path);
            ImageIO.write(image, "png", file);
        } catch (IOException e){
            System.err.println("Error while saving image: " + e.getMessage());
        }
    }
    public void increaseBrightness(int factor) {
        int width = image.getWidth();
        int height = image.getHeight();
        int mask = 0b11111111;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                rgb = brightenPixel(rgb, factor);
                image.setRGB(x, y, rgb);
            }
        }
    }
    private int brightenPixel(int rgb, int factor) {
        int blue = (rgb & 255);
        int green = (rgb >> 8) & 255;
        int red = (rgb >> 16) & 255;
        blue = increaseColor(blue, factor);
        green = increaseColor(green, factor);
        red = increaseColor(red, factor);
        rgb = blue + (green << 8) + (red << 16);
        return rgb;
    }
    private int increaseColor(int color, int factor) {
        return Math.min(color + factor, 255);
    }
    public void increaseBrightnessByThreading(int factor) {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < threads.length; i++) {
            final int finalI = i;
            threads[i] = new Thread(() -> {
                int start = image.getHeight() * finalI / numberOfThreads;
                int end = start + image.getHeight() / numberOfThreads;
                System.out.println("Thread " + finalI + " processing lines from " + start + " to " + end);
                if (finalI == threads.length - 1) {
                    end = image.getHeight();
                }
                for (int x = start; x < end; x++) {
                    for (int y = 0; y < image.getWidth(); y++) {
                        int pixel = image.getRGB(y, x);
                        pixel = brightenPixel(pixel, factor);
                        image.setRGB(y, x, pixel);
                    }
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void increaseBrightnessOneLineOneThread(int factor) {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < threads.length; i++) {
            final int finalI = i;
            threads[i] = new Thread(() -> {
                for (int y = finalI; y < image.getHeight(); y += numberOfThreads) {
                    for (int x = 0; x < image.getWidth(); x++) {
                        int pixel = image.getRGB(x, y);
                        pixel = brightenPixel(pixel, factor);
                        image.setRGB(x, y, pixel);
                    }
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public int[] calculateHistogram(int channel) throws ExecutionException, InterruptedException {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        Future<int[]>[] futures = new Future[numberOfThreads];
        int width = image.getWidth();
        int height = image.getHeight();
        int rowsPerThread = height / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            final int startRow = i * rowsPerThread;
            final int endRow = (i == numberOfThreads - 1) ? height : startRow + rowsPerThread;

            futures[i] = executor.submit(() -> {
                int[] histogram = new int[256];
                for (int x = 0; x < width; x++) {
                    for (int y = startRow; y < endRow; y++) {
                        int rgb = image.getRGB(x, y);
                        int color = (rgb >> (8 * channel)) & 255;
                        histogram[color]++;
                    }
                }
                return histogram;
            });
        }

        int[] histogram = new int[256];
        for (Future<int[]> future : futures) {
            int[] partialHistogram = future.get();
            for (int i = 0; i < histogram.length; i++) {
                histogram[i] += partialHistogram[i];
            }
        }

        executor.shutdown();

        return histogram;
    }
    public void drawHistogram(int[] histogram){
        int width = 256;
        int height = 100;
        BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int max = 0;
        for (int i = 0; i < histogram.length; i++) {
            max = Math.max(max, histogram[i]);
        }
        for (int x = 0; x < width; x++) {
            int value = histogram[x] * height / max;
            for (int y = 0; y < height; y++) {
                int color = (y < value) ? 0xFF000000 : 0xFFFFFFFF;
                histogramImage.setRGB(x, height - y - 1, color);
            }
        }
        image = histogramImage;
    }
    public void drawGradientByChannel(int channel) {
        BufferedImage gradient = new BufferedImage(256, 100, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < 256; x++) {
            for (int y = 0; y < 100; y++) {
                int color = 0;
                color = (x << (8 * channel)) | 0xFF000000;
                gradient.setRGB(x, y, color);
            }
        }
        image = gradient;
    }
    public void drawUkraineFlag() {
        BufferedImage flag = new BufferedImage(256, 100, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < 256; x++) {
            for (int y = 0; y < 50; y++) {
                int color = 0;
                color = x | 0xFF000000;
                flag.setRGB(x, y, color);
            }
        }
        for (int x = 0; x < 256; x++) {
            for (int y = 50; y < 100; y++) {
                int color = 0;
                color = (x << 8) | 0xFF000000 + (x << 16) | 0xFF000000;
                flag.setRGB(x, y, color);
            }
        }
        image = flag;
    }





}
