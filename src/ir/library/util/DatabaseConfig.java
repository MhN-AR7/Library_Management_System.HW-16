package ir.library.util;

import ir.library.exception.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/hw16";
    private static final String Username = "postgres";
    private static final String Password = "2117";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, Username, Password);
        }
        catch (SQLException e) {
            throw new DatabaseConnectionException("Connection Failed!");
        }
    }
}
