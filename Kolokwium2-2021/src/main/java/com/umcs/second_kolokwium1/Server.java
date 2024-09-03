package com.umcs.second_kolokwium1;

import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private final ServerSocket serverSocket;
    private final List<ClientHandler> clients;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.clients = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Error starting server", e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                ClientHandler clientHandler = new ClientHandler(serverSocket.accept(), this);
                addClient(clientHandler);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                throw new RuntimeException("Error accepting client connection", e);
            }
        }
    }

    public synchronized void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public synchronized void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public synchronized void setColor(String hexColor, ClientHandler clientHandler) {
        Color color = Color.web("#" + hexColor);
        DrawApplication.setColor(clientHandler, color);
    }

    public synchronized void setPoints(String points, ClientHandler clientHandler) {
        DrawApplication.drawLines(points, clientHandler);
    }
}
