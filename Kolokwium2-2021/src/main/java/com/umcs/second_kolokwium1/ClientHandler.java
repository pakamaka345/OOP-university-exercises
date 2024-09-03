package com.umcs.second_kolokwium1;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Server server;
    private final Scanner input;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.server = server;
            this.input = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error creating client handler", e);
        }
    }

    @Override
    public void run() {
        while (true) {
            if (input.hasNextLine()) {
                String message = input.nextLine();
                System.out.println("Received message: " + message);
                if (message.split(" ").length == 4) {
                    server.setPoints(message, this);
                } else if (message.length() == 6) {
                    server.setColor(message, this);
                } else {
                    System.out.println("Invalid message: " + message);
                }
            }

            if (socket.isClosed()) {
                disconnect();
                break;
            }
        }
    }

    private void disconnect() {
        try {
            input.close();
            socket.close();
            server.removeClient(this);
        } catch (IOException e) {
            throw new RuntimeException("Error disconnecting client", e);
        }
    }
}
