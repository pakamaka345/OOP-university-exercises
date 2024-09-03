package com.example.NikitaLysiuk.client;

import com.example.NikitaLysiuk.controller.RegisterController;
import com.example.NikitaLysiuk.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
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

    }
}
