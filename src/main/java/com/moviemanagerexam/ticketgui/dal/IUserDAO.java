package com.moviemanagerexam.ticketgui.dal;

import com.moviemanagerexam.ticketgui.be.User;
import java.util.List;

public interface IUserDAO {
    List<User> getAllUsers() throws Exception;
    User createUser(String username, String password, User.Role role) throws Exception;
    void updateUser(User user) throws Exception;
    void deleteUser(User user) throws Exception;
    User login(String username, String password) throws Exception;
}
