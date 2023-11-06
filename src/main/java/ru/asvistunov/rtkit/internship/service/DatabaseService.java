package ru.asvistunov.rtkit.internship.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/rtkit_intership";
    private static final String DATABASE_USER = "test";
    private static final String DATABASE_PASSWORD = "test";

    private static Connection connection;

    public static Connection connect(){
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
