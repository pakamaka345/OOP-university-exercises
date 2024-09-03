package com.umcs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class processor {
        public BufferedImage image;
        public void loadImage(String path) {
            try {
                File file = new File(path);
                image = ImageIO.read(file);
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
        public void saveImage(String path) {
            String format = "jpg";
            if (path.lastIndexOf('.') != -1) {
                format = path.substring(path.lastIndexOf('.') + 1);
            }
            try {
                ImageIO.write(image, format, new File(path));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        public void increaseBrightness(int factor) {
            int width = image.getWidth();
            int height = image.getHeight();
            int r, g, b;
            for (int y = 0; y < height; y++){
                for (int x = 0; x < width; x++){
                    int rgb = image.getRGB(x, y);
                    //System.out.println("RGB signed 32 bit = "+ rgb +" hex: " + Integer.toHexString(rgb));
                    b = rgb & 255;
                    g = (rgb >> 8) & 255;
                    r = (rgb >> 16) & 255;
                    b = Math.clamp(b + factor, 0, 255);
                    g = Math.clamp(g + factor, 0, 255);
                    r = Math.clamp(r + factor, 0, 255);
                    int newRGB = (r << 16) | (g << 8) | b;
                    image.setRGB(x, y, newRGB);
                }
            }
        }
        public void increaseBrightnessMulti(int factor) throws InterruptedException {
            int height = image.getHeight();
            int cores = Runtime.getRuntime().availableProcessors();
            Thread[] threads = new Thread[cores];
            int chunkSize = height / cores;

            for (int i = 0; i < cores; i++) {
                int start = i * chunkSize;
                int end = (i == cores - 1) ? height : (i + 1) * chunkSize;

                threads[i] = new Thread(new BrightnessWorker(start, end, image, factor));
                threads[i].start();
            }

            for (Thread thread : threads) thread.join();
        }
}
