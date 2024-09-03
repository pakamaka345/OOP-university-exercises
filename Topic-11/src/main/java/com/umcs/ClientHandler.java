package com.umcs;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Server server;
    private final Scanner input;
    private final PrintWriter output;
    private String userName;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.server = server;
            this.input = new Scanner(socket.getInputStream());
            this.output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException("Error creating client handler", e);
        }
    }

    @Override
    public void run() {
        try {
            boolean connected = isClientConnected();

            if (!connected) {
                return;
            }

            join();
            comunicate();
            disconnect();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private boolean isClientConnected() throws IOException {
        if (input.hasNextLine()) {
            userName = input.nextLine();

            if (server.isClientNameTaken(userName)) {
                output.println("User already exists");
                socket.close();
                return false;
            }
            return true;
        }
        return false;
    }

    private void join() {
        server.addClient(userName, this);
        server.printUsers();
        server.broadcast("/broadcast [SERVER]: " + userName + " joined!");
        server.broadcast("/online " + server.getClientNames());
    }

    private void comunicate() throws IOException {
        String clientMessage;
        boolean connected = true;

        while (connected) {
            if (input.hasNextLine()) {
                clientMessage = input.nextLine();
                connected = parseClientMessage(clientMessage);
            } else {
                connected = false;
            }
        }
    }

    public boolean parseClientMessage(String clientMessage) {
        String command = getCommand(clientMessage);

        switch (command) {
            case "/w":
                parsePrivateMessage(clientMessage);
                break;
            case "/online":
                this.send("/online " + server.getClientNames());
                break;
            default:
                server.broadcast("/broadcast [" + userName + "]: " + clientMessage);
                break;
        }
        return !clientMessage.equalsIgnoreCase("bye");
    }

    private String getCommand(String clientMessage) {
        if (clientMessage.startsWith("/w"))
            return "/w";
        else if (clientMessage.equals("/online"))
            return "/online";
        else
            return "default";
    }

    private void parsePrivateMessage(String clientMessage) {
        String[] message = clientMessage.split(" ", 3);
        if (message.length >= 3) {
            String rName = message[1];
            String messageToSend = "/broadcast " + message[2];
            server.broadcastPrivate(userName, rName, messageToSend);
        }
    }

    private void disconnect() throws IOException {
        server.removeClient(userName);
        socket.close();
        server.printUsers();
        server.broadcast("/broadcast [SERVER]: " + userName + " left");
        server.broadcast("/online " + server.getClientNames());
    }

    public void send(String message) {
        if (output != null) {
            output.println(message);
        }
    }
}
