package com.yura.workshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * Provides JDBC connections configured via environment variables.
 * Defaults are set for local Docker Compose development.
 *
 * Environment variables:
 *   DB_URL      — JDBC URL  (default: jdbc:postgresql://localhost:5432/yura_workshop)
 *   DB_USER     — username  (default: postgres)
 *   DB_PASSWORD — password  (default: postgres)
 */
public class DatabaseConnection {

    private static final Map<String, String> ENV = System.getenv();

    private static final String URL =
            ENV.getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/yura_workshop");
    private static final String USER =
            ENV.getOrDefault("DB_USER", "postgres");
    private static final String PASSWORD =
            ENV.getOrDefault("DB_PASSWORD", "postgres");

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
