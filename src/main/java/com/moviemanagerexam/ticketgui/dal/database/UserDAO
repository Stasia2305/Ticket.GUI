package com.moviemanagerexam.ticketgui.dal.database;

import com.moviemanagerexam.ticketgui.be.User;
import com.moviemanagerexam.ticketgui.dal.DBConnector;
import com.moviemanagerexam.ticketgui.dal.IUserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private final DBConnector dbConnector;

    public UserDAO(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        String sql = "SELECT id, username, [password], [role] FROM dbo.users ORDER BY username";
        List<User> users = new ArrayList<>();

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                users.add(mapUser(rs));
            }
        }

        return users;
    }

    @Override
    public User createUser(String username, String password, User.Role role) throws Exception {
        String duplicateCheckSql = "SELECT 1 FROM dbo.users WHERE username = ?";
        String insertSql = "INSERT INTO dbo.users (username, [password], [role]) VALUES (?, ?, ?)";

        try (Connection connection = dbConnector.getConnection()) {
            if (usernameExists(connection, duplicateCheckSql, username)) {
                throw new Exception("Username already exists.");
            }

            try (PreparedStatement statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, role.name());
                statement.executeUpdate();

                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        return new User(keys.getInt(1), username, password, role);
                    }
                }
            }
        }

        throw new SQLException("Failed to create user.");
    }

    @Override
    public void updateUser(User user) throws Exception {
        String sql = "UPDATE dbo.users SET username = ?, [password] = ?, [role] = ? WHERE id = ?";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().name());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteUser(User user) throws Exception {
        String sql = "DELETE FROM dbo.users WHERE id = ?";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public User login(String username, String password) throws Exception {
        String sql = "SELECT id, username, [password], [role] FROM dbo.users WHERE username = ? AND [password] = ?";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        }

        return null;
    }

    private boolean usernameExists(Connection connection, String sql, String username) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                User.Role.valueOf(rs.getString("role"))
        );
    }
}
