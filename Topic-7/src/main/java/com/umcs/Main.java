package com.umcs;

import com.umcs.auth.AccountManager;
import com.umcs.database.DatabaseConnection;

import java.nio.file.Path;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.connect(Path.of("site.db"));
        AccountManager accountManager = new AccountManager(databaseConnection);
        accountManager.init();
        accountManager.register("test", "12345");
        var temp = accountManager.getAccount("test");
        System.out.println(temp.toString());


        System.out.println(accountManager.authenticate("test", "12345"));

        databaseConnection.disconnect();
    }
}
