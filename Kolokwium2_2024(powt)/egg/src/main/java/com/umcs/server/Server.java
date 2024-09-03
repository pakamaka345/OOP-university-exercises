package com.umcs.server;

import com.umcs.client.ClientHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private Map<String, ClientHandler> clients = new HashMap<>();
    private Connection connection;

    public static void main(String[] args) {
        Server server = new Server();
        server.start(8080);
    }

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                ClientHandler clientHandler = new ClientHandler(this, socket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    public void addClient(String username, ClientHandler clientHandler) {
        clients.put(username, clientHandler);
    }
    public String createImage(String username, String line, int electrodeNumber) {
        String base64 = "";
        if (line.equals("bye")) {
            clients.get(username).removeClient();
            clients.remove(username);
            return base64;
        }

        double[] data = Arrays.stream(line.split(","))
                .mapToDouble(Double::parseDouble)
                .toArray();

        System.out.println(Arrays.toString(data));

        BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 200, 100);

        graphics.setColor(Color.RED);
        for (int i = 0; i < data.length; i++) {
            int x = i;
            int y = 50 - (int) data[i];
            graphics.fillRect(x, y, 1, 1);
        }

        graphics.dispose();

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            base64 = Base64.getEncoder().encodeToString(imageBytes);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            System.err.println("Error creating image: " + e.getMessage());
        }

        createConnection();
        insertData(username, electrodeNumber, base64);
        closeConnection();

        return base64;
    }

    private void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\project\\Java\\egg\\src\\main\\resources\\usereeg.db");
        } catch (SQLException e) {
            System.err.println("Error creating connection: " + e.getMessage());
        }
    }
    private void insertData(String username, int electrodeNumber, String image) {
        String insertDataSQL = "INSERT INTO user_eeg (username, electrode_number, image) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertDataSQL);
            statement.setString(1, username);
            statement.setInt(2, electrodeNumber);
            statement.setString(3, image);
            statement.executeUpdate();
            System.out.println("Data inserted");
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }
    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    public void displayUsername(String username) {
        System.out.println("Username: " + username);
    }
}
