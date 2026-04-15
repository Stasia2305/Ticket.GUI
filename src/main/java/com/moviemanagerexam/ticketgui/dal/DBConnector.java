package com.moviemanagerexam.ticketgui.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
    private SQLServerDataSource dataSource;

    public DBConnector() {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("127.0.0.1"); // Placeholder
        dataSource.setDatabaseName("TicketSystem"); // Placeholder
        dataSource.setUser("user"); // Placeholder
        dataSource.setPassword("password"); // Placeholder
        dataSource.setPortNumber(1433);
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
}
