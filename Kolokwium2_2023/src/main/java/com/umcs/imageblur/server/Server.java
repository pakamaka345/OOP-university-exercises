package com.umcs.imageblur.server;

import com.umcs.imageblur.HelloApplication;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;


public class Server extends Application {
    private String url = "jdbc:sqlite:C:\\project\\java\\imageBlur\\database.db";
    private boolean isClientConnected = false;
    private static int kernelRadius = 1;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication kernelSettings = new HelloApplication();
        kernelSettings.start(new Stage());

        new Thread(() -> listen(8080)).start();
        kernelRadius = kernelSettings.getKernelRadius();
        System.out.println(kernelRadius);
    }
    public void listen(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                if(!isClientConnected) {
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected!");
                    isClientConnected = true;
                    handleClient(socket);
                    isClientConnected = false;
                }
            }
        } catch (IOException e) {
            System.err.println("Error while listening on port " + port);
        }
    }
    private void handleClient(Socket clientSocket) throws IOException {
        try (InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream()) {
            BufferedImage bufferedImage = ImageIO.read(input);

            Path dirPath = Paths.get("images");
            LocalDateTime name = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");
            Path filePath = dirPath.resolve(name.format(formatter) + ".png");

            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            ImageIO.write(bufferedImage, "png", filePath.toFile());

            long runTime = blur(bufferedImage);

            create();
            insert(filePath.toString(), kernelRadius, (int)runTime);

            ImageIO.write(bufferedImage, "png", output);
            clientSocket.close();
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        } finally {
            clientSocket.close();
        }
    }

    private long blur(BufferedImage image) throws InterruptedException {
        long startTime = System.nanoTime();

        int kernels = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(kernels);
        int height = image.getHeight();
        int width = image.getWidth();
        int rowsPerThread = height / kernels;

        BufferedImage copy = new BufferedImage(width, height, image.getType());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                copy.setRGB(x, y, image.getRGB(x, y));
            }
        }

        for (int k = 0; k < kernels; k++) {
            final int start = k * rowsPerThread;
            final int end = (k == kernels - 1) ? height : start + rowsPerThread;

            executor.submit(() -> {
                for (int x = 0; x < width; x++) {
                    for (int y = start; y < end; y++) {
                        int pixelCount = 0, r = 0, g = 0, b = 0;

                        for (int i = -kernelRadius; i <= kernelRadius; i++) {
                            for (int j = -kernelRadius; j <= kernelRadius; j++) {
                                int currentX = x + i;
                                int currentY = y + j;

                                if (currentX >= 0 && currentX < image.getWidth() && currentY >= 0 && currentY < image.getHeight()) {
                                    int pixel = copy.getRGB(currentX, currentY);

                                    r += (pixel >> 16) & 0xFF;
                                    g += (pixel >> 8) & 0xFF;
                                    b += pixel & 0xFF;

                                    pixelCount++;
                                }
                            }
                        }
                        int newRed = r / pixelCount;
                        int newGreen = g / pixelCount;
                        int newBlue = b / pixelCount;

                        int newPixel = (newRed << 16) | (newGreen << 8) | newBlue;
                        image.setRGB(x, y, newPixel);
                    }
                }
            });

        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000;
    }

    private void create() {

        String CreateTableSQL = "CREATE TABLE IF NOT EXISTS images (" +
                "id INTEGER PRIMARY KEY," +
                "path TEXT NOT NULL," +
                "size INTEGER NOT NULL," +
                "delay INTEGER NOT NULL);";

        try (Connection connection = DriverManager.getConnection(url)) {
            Statement statement = connection.createStatement();
            statement.execute(CreateTableSQL);
            System.out.println("ok");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void insert(String path, int size, int delay) {
        String Insert = "INSERT INTO images (" +
                "path, size, delay) VALUES (" +
                "?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(url)) {
            PreparedStatement statement = connection.prepareStatement(Insert);
            statement.setString(1, path);
            statement.setInt(2, size);
            statement.setInt(3, delay);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
