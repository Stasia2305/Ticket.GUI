package com.moviemanagerexam.ticketgui.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
    private SQLServerDataSource dataSource;

    public DBConnector() {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName(readConfig("db.server", "DB_SERVER", "127.0.0.1"));
        dataSource.setDatabaseName(readConfig("db.name", "DB_NAME", "TicketSystem"));
        dataSource.setUser(readConfig("db.user", "DB_USER", "sa"));
        dataSource.setPassword(readConfig("db.password", "DB_PASSWORD", ""));
        dataSource.setPortNumber(Integer.parseInt(readConfig("db.port", "DB_PORT", "1433")));
        dataSource.setTrustServerCertificate(true);
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) {
        DBConnector dbConnector = new DBConnector();
        try (Connection connection = dbConnector.getConnection()) {
            System.out.println("Connection established!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String readConfig(String propertyKey, String envKey, String defaultValue) {
        String propertyValue = System.getProperty(propertyKey);
        if (propertyValue != null && !propertyValue.isBlank()) {
            return propertyValue;
        }

        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        return defaultValue;
    }
}