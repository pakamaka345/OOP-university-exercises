package com.example.NikitaLysiuk.controller;

import com.example.NikitaLysiuk.model.Pixel;
import com.example.NikitaLysiuk.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

@RestController
public class RegisterController {
    private String url = "jdbc:sqlite:~/Pulpit/NikitaLysiuk/NikitaLysiuk/database.db";
    private List<Token> tokens = new ArrayList<>();
    private boolean isClientConnected = false;
    private static final String password = "StrongPassword";
    public static BufferedImage image;
    @Autowired
    public RegisterController() {
        if (image == null) {
            image = new BufferedImage(512, 512, BufferedImage.TYPE_3BYTE_BGR);
            for (int x = 0; x < 512; x++) {
                for (int y = 0; y < 512; y++) {
                    image.setRGB(x, y, 0);
                }
            }
        }
        createSql();
        createImage();
        new Thread(() -> {
            start(12345);
        }).start();
    }
    @PostMapping("/register")
    public Token register() {
        UUID token = UUID.randomUUID();
        LocalTime time = LocalTime.now();

        Token userToken = new Token(token, time, Token.Status.NonActive);
        checkFiveMinute();
        tokens.add(userToken);
        return userToken;
    }
    private void checkFiveMinute() {
        for (Token token : tokens) {
            if (LocalTime.now().getMinute() - token.getTime().getMinute() > 5) {
                token.setStatus(Token.Status.Active);
            }
        }
    }

    @GetMapping("/tokens")
    public List<Token> getTokens() {
        return tokens;
    }


    @PostMapping("/pixel")
    public ResponseEntity<String> postPixel(@RequestBody Pixel pixel) {
        if ((pixel.getX() < 0 || pixel.getX() >= 512) || (pixel.getY() < 0 || pixel.getY() >= 512)) {
            return new ResponseEntity<>("bad x/y", HttpStatus.BAD_REQUEST);
        }
        if (pixel.getToken().getStatus() == Token.Status.NonActive) {
            return new ResponseEntity<>("nonActive", HttpStatus.FORBIDDEN);
        }

        insertInto(pixel);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    private void insertInto(Pixel pixel) {
        String sql = "INSERT INTO entry (token, x, y, color, timestamp) VALUES(?, ?, ?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(url)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pixel.getToken().getToken().toString());
            statement.setInt(2, pixel.getX());
            statement.setInt(3, pixel.getY());
            statement.setString(4, pixel.getColor());
            statement.setString(5, pixel.getToken().getTime().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createSql() {
        String sql = "CREATE TABLE IF NOT EXISTS entry (token TEXT NOT NULL, x INTEGER NOT NULL, y INTEGER NOT NULL, color TEXT NOT NULL, timestamp TEXT NOT NULL);";
        try (Connection connection = DriverManager.getConnection(url)) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createImage() {
        String sql = "SELECT token, x, y, color FROM entry ORDER BY timestamp;";

        try (Connection connection = DriverManager.getConnection(url)) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet result = statement.getResultSet();
            while (result.next()) {
                Color c = Color.decode("#" + result.getString("color"));
                image.setRGB(result.getInt("x"), result.getInt("y"), c.getRGB());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                if (!isClientConnected) {
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected!");
                    isClientConnected = true;
                    handleClient(socket);
                    isClientConnected = false;
                }
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }
    private void handleClient(Socket socket) {
        try (Scanner scanner = new Scanner(socket.getInputStream()); PrintWriter output = new PrintWriter(socket.getOutputStream());)  {
            String inputPassword = scanner.nextLine();
            if (inputPassword != password) {
                socket.close();
            }

            String command = scanner.nextLine();
            String[] commands = command.split(" ");
            int count = 0;
            if (commands[0].equals("ban")) {
                for (int i = 0; i < tokens.size(); i++) {
                    if (tokens.get(i).getToken().toString().equals(commands[1])) {
                        tokens.remove(i);

                    }
                }

                try (Connection connection = DriverManager.getConnection(url)) {
                    String sql = "DELETE FROM entry WHERE token=?;";

                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, commands[1]);
                    statement.executeUpdate();
                    count = statement.getUpdateCount();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
                createImage();
                output.println("Number of deleted records is: " + count);
            }
            if (commands[0].equals("video")) {

            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
