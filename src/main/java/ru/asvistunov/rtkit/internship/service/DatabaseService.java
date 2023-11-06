package ru.asvistunov.rtkit.internship.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс DatabaseService предоставляет методы для управления подключением к базе данных.
 */
public class DatabaseService {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/rtkit_intership";
    private static final String DATABASE_USER = "test";
    private static final String DATABASE_PASSWORD = "test";

    private static Connection connection;

    /**
     * Метод connect() устанавливает соединение с базой данных и возвращает объект Connection.
     *
     * @return Объект Connection для работы с базой данных.
     */
    public static Connection connect() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Метод getConnection() возвращает текущее активное соединение с базой данных.
     *
     * @return Текущее соединение с базой данных.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Метод disconnect() закрывает текущее соединение с базой данных, если оно активно.
     */
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
