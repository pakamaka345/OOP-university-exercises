package com.umcs.database;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }
    public void connect(Path path) {
        try {
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", path));
            System.out.println("Connect to Database");
        } catch (SQLException e){
            System.err.println(e.getErrorCode());
        }
    }
    public void disconnect() {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
            System.out.println("Disconnect");
        } catch (SQLException e){
            System.err.println(e.getErrorCode());
        }
    }
}
