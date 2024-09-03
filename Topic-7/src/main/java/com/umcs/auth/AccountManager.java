package com.umcs.auth;

import com.umcs.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {
    DatabaseConnection databaseConnection;
    public AccountManager(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    public void init(){
        try {
            String createSQLTable = "CREATE TABLE IF NOT EXISTS accounts( " +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "password TEXT NOT NULL)";


            PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(createSQLTable);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.err.println(e.getErrorCode());
        }
    }

    public boolean register(String username, String password){
        String insertSql = "INSERT INTO accounts (username, password) VALUES (?, ?)";
        Connection connection = databaseConnection.getConnection();
        Account account = getAccount(username);
        if (account != null){
            return false;
        }
        try{

            PreparedStatement statement = connection.prepareStatement(insertSql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException e){
            System.err.println(e.getErrorCode());
        }
        return true;
    }
    public Account getAccount(String username) {
        try {
            String query = "SELECT id, username FROM accounts WHERE username = ?";
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String usernameNew = resultSet.getString("username");
                return new Account(usernameNew, id);
            }
        } catch (SQLException e){
            System.err.println(e.getErrorCode());
        }
        return null;
    }
    public boolean authenticate(String login, String password){
        String query = "SELECT password FROM accounts WHERE username = ?";
        try{
            Connection connection = databaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String passwordStore = resultSet.getString("password");
                if (password.equals(passwordStore)){
                    return true;
                }
            }
        }catch (SQLException e){
            System.err.println(e.getErrorCode());
        }
        return false;
    }
}
