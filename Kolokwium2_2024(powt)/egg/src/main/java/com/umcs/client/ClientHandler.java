package com.umcs.client;

import com.umcs.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private final Server server;
    private final Socket socket;
    private final Scanner input;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.input = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error creating client handler", e);
        }
    }

    @Override
    public void run() {
        String username = input.nextLine();
        server.addClient(username, this);
        String path = input.nextLine();
        sendData(path, username);
    }

    private void sendData(String path, String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
            String line;
            server.displayUsername(username);
            int i = 0;
            while ((line = reader.readLine()) != null){
                server.createImage(username, line, i);
                Thread.sleep(2000);
                i++;
            }
            String image = server.createImage(username, "bye", -1);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void removeClient() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }
    }
}
