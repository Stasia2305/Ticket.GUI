package com.moviemanagerexam.ticketgui.dal.mock;

import com.moviemanagerexam.ticketgui.be.User;
import com.moviemanagerexam.ticketgui.dal.IUserDAO;

import java.util.ArrayList;
import java.util.List;

public class MockUserDAO implements IUserDAO {
    private List<User> users = new ArrayList<>();

    public MockUserDAO() {
        users.add(new User(1, "admin", "admin", User.Role.ADMIN));
        users.add(new User(2, "coordinator", "coordinator", User.Role.EVENT_COORDINATOR));
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User createUser(String username, String password, User.Role role) {
        User user = new User(users.size() + 1, username, password, role);
        users.add(user);
        return user;
    }

    @Override
    public void updateUser(User user) {
        // Mock update
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
    }

    @Override
    public User login(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
