package com.umcs.imageblur;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;

public class Client {

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 8080;

        try {
            // Генеруємо випадкове зображення
            BufferedImage image = generateRandomImage(400, 400);

            // Підключаємось до сервера
            Socket socket = new Socket(serverAddress, serverPort);

            // Відправляємо зображення серверу
            sendImage(image, socket.getOutputStream());

            // Закриваємо сокет
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage generateRandomImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        // Заповнюємо зображення випадковими кольорами
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color randomColor = new Color((int)(Math.random() * 0x1000000));
                g.setColor(randomColor);
                g.fillRect(x, y, 1, 1);
            }
        }

        g.dispose();
        return image;
    }

    private static void sendImage(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image, "png", out);
        out.flush();
    }
}