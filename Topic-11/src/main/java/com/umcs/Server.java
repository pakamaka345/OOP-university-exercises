package com.umcs;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final Map<String, ClientHandler> clients = new HashMap<>();

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Server started on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    public void addClient(String userName, ClientHandler clientHandler) {
        clients.put(userName, clientHandler);
    }

    public void broadcast(String message) {
        System.out.println("Broadcasting message: " + message);
        for (ClientHandler client : clients.values()) {
            client.send(message);
        }
    }

    public void removeClient(String userName) {
        ClientHandler user = clients.remove(userName);
        if (user != null) {
            System.out.println("Client " + userName + " removed");
        }
    }

    public void printUsers() {
        System.out.println("Users connected: " + clients.keySet());
    }

    public String getClientNames() {
        return clients.keySet().toString();
    }

    public void broadcastPrivate(String sender, String receiver, String message) {
        ClientHandler receiverHandler = clients.get(receiver);
        if (receiverHandler != null) {
            receiverHandler.send("/broadcast [ _private_ " + sender + " ]: " + message);
        } else {
            clients.get(sender).send("/broadcast [SERVER]: User offline");
        }
    }
    public boolean isClientNameTaken(String userName) {
        return clients.containsKey(userName);
    }
}
